package com.example.controller;
import com.example.config.utils.DateConverter;
import com.example.config.utils.FileExporter;
import com.example.config.utils.ZipCompression;
import com.example.domain.Course;
import com.example.domain.UserCourse;
import com.example.service.ICourseService;
import com.example.service.ITeacherService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("task")
public class TaskController
{
    @Autowired
    private ICourseService courseService;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private IUserService userService;
    @Autowired
    private ITeacherService teacherService;

    /**
     * 查询某个老师下已发布的课程
     * @param teacherId 老师id
     */
    @RequestMapping("")
    public List<Course> queryCourseByTeacher(Integer teacherId)
    {
        return courseService.queryCourseByTeacher(teacherId);
    }

    /**
     * 给实验课上传资料文件
     * @param courseId 课程id
     * @param multipartFile 文件 格式不限 大小小于20MB
     */
    @RequestMapping(value = "/template/post",method = RequestMethod.POST)
    public String postTemplate(Integer courseId,@RequestParam("file") MultipartFile multipartFile) throws IOException
    {
        if(multipartFile.isEmpty()){
            return "f";
        }
        File dir = new File(getClass().getResource(".").getFile(),"course"+courseId);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File[] files = dir.listFiles();
        if(files!=null&&files.length!=0) files[0].delete();
        File file = new File(getClass().getResource("./course"+courseId).getFile(), Objects.requireNonNull(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(file);
        return "0";
    }

    /**
     * 下载文件
     * @param courseId 课程id
     * @return 文件流
     */
    @RequestMapping(value = "/template/download",method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> downloadATemplate(Integer courseId)
    {
        File dir = new File(getClass().getResource(".").getFile(),"course"+courseId);
        if(!dir.exists()) return null;
        File[] files = dir.listFiles();
        if(files==null||files.length==0) return null;
        return FileExporter.export(files[0]);
    }

    /**
     * 查看作业要求
     * @param courseId 课程id
     * @return json
     */
    @RequestMapping("/homework")
    public Map<String,String> getHomeworkRequirement(Integer courseId)
    {
        Jedis jedis = jedisPool.getResource();
        Map<String,String> map = new HashMap<>();
        if(!jedis.exists("course"+courseId+"requirement")) return map;
        map = jedis.hgetAll("course"+courseId+"requirement");
        jedis.close();
        return map;
    }

    /**
     * 发布作业要求
     * @param courseId 课程id
     * @param isTeamwork 是否合作完成
     * @param deadline 截止时间
     */
    @RequestMapping(value = "/homework/publish",method = RequestMethod.POST)
    public String publishHomework(Integer courseId,Boolean isTeamwork,String deadline)
    {
        Date date = DateConverter.convert2(deadline);
        Date now = new Date();
        long seconds = (date.getTime()-now.getTime());
        if(seconds<=0) return "错误，时间已过期";
        Course course = courseService.getCourse(courseId);
        if(!course.getIs_team() && isTeamwork) return "非组队类型课程，作业不能设置为合作完成";
        Jedis jedis = jedisPool.getResource();
        jedis.hset("course"+courseId+"requirement","isTeamwork",isTeamwork.toString());
        jedis.hset("course"+courseId+"requirement","deadline",deadline);
        jedis.save();
        jedis.close();
        return "0";
    }

    /**
     * 查看已交报告名单 未批改在前 已批改在后
     * @param courseId 学号的id
     * @return 学生的名单，带成绩
     */
    @RequestMapping("/homework/list")
    public List<UserCourse> homeworkList(Integer courseId)
    {
        List<UserCourse> userList = new ArrayList<>();
        File dir = new File(getClass().getResource(".").getFile(), "course_"+courseId+"_report");
        if (!dir.exists()) return userList;
        String[] files = dir.list();
        if(files==null) return userList;
        Set<Long> set = new HashSet<>();
        for(String file : files){
            Long userId = Long.parseLong(file.substring(0,file.lastIndexOf('.')));
            set.add(userId);
        }
        List<UserCourse> userList2 = new ArrayList<>();
        for(Long userId : set){
            UserCourse userCourse = userService.getUserCourseWithUser(userId,courseId);
            if(userCourse.getScore()==null){
                userList.add(userCourse);
            }else{
                userList2.add(userCourse);
            }
        }
        userList.addAll(userList2);
        return userList;
    }

    /**
     * @param courseId 课程id
     * @return 未批改报告的学号数组
     */
    @RequestMapping("/homework/userList")
    public List<Long> homeworkUsernameList(Integer courseId){
        List<Long> userList = new ArrayList<>();
        File dir = new File(getClass().getResource(".").getFile(), "course_"+courseId+"_report");
        if (!dir.exists()) return userList;
        String[] files = dir.list();
        if(files==null) return userList;
        for(String file : files){
            Long userId = Long.parseLong(file.substring(0,file.lastIndexOf('.')));
            userList.add(userId);
        }
        return userList;
    }

    /**
     * 批阅报告 打开pdf预览器
     * @param userId 学号
     * @param courseId 课程id
     */
    @RequestMapping("/homework/check")
    public void homeworkCheckPage(Long userId, Integer courseId, HttpServletResponse response)
    {
        response.setContentType("application/pdf");
        FileInputStream in;
        OutputStream out;
        try {
            in = new FileInputStream(new File(getClass().getResource("./course_"+courseId+"_report").getFile(),userId+".pdf"));
            out = response.getOutputStream();
            byte[] b = new byte[512];
            while ((in.read(b)) != -1) {
                out.write(b);
            }
            out.flush();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给报告打分批改
     * @param courseId 课程id
     * @param userId 学号
     * @param score 成绩
     * @param teacherId 操作老师id
     */
    @RequestMapping(value = "/homework/mark",method = RequestMethod.POST)
    public String markTheHomework(Integer courseId,Long userId,Float score,Integer teacherId)
    {
        Jedis jedis = jedisPool.getResource();
        boolean isTeamwork=false;
        if(jedis.exists("course"+courseId+"requirement")){
            isTeamwork=Boolean.parseBoolean(jedis.hget("course"+courseId+"requirement","isTeamwork"));
        }
        String teacher = teacherService.getTeacher(teacherId).getName();
        if(isTeamwork){
            UserCourse userCourse = userService.queryUserCourseWithCourse(userId,courseId);
            Integer teamId = userCourse.getTeam_id();
            List<Long> usernameList = userService.queryUsernameByTeamId(teamId);
            for(Long username : usernameList){
                userService.updateScore(username,courseId,score,teacher);
            }
        }else{
            userService.updateScore(userId,courseId,score,teacher);
        }
        jedis.close();
        return "0";
    }

    /**
     * 批量下载文件
     * @param courseId 课程id
     */
    @RequestMapping("/homework/download/all")
    public ResponseEntity<FileSystemResource> downloadAll(Integer courseId)
    {
        File file = new File(getClass().getResource(".").getFile(),"course_"+courseId+"_report");
        if(!file.exists()) return null;
        ZipCompression.filetoZip("Course_"+courseId+"_report.zip",file);
        File zip = new File("Course_"+courseId+"_report.zip");
        if(!zip.exists()) return null;
        return FileExporter.export(zip);
    }

    /**
     * 下载单个文件
     * @param courseId 课程id
     * @param userId 学号
     */
    @RequestMapping("homework/download/one")
    public ResponseEntity<FileSystemResource> downloadOne(Integer courseId,Long userId)
    {
        File file = new File(getClass().getResource("./course_"+courseId+"_report").getFile(),userId+".pdf");
        if(!file.exists()) return null;
        return FileExporter.export(file);
    }
}
