package com.example.controller;

import com.example.config.utils.DateConverter;
import com.example.config.utils.FileExporter;
import com.example.domain.Course;
import com.example.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("task")
public class TaskController
{
    @Autowired
    private ICourseService courseService;
    @Autowired
    private JedisPool jedisPool;

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
     * 查看课程资料文件名单
     * @param courseId 课程id
     * @return 文件名的数组
     */
    @RequestMapping(value = "/template",method = RequestMethod.GET)
    public String[] getTemplate(Integer courseId)
    {
        File dir = new File(getClass().getResource(".").getFile(),"course"+courseId);
        if(!dir.exists()) return new String[0];
        return dir.list();
    }

    /**
     * 给实验课上传资料文件
     * @param courseId 课程id
     * @param multipartFile 文件
     */
    @RequestMapping(value = "/template/post",method = RequestMethod.POST)
    public String postTemplate(Integer courseId,@RequestParam("file") MultipartFile multipartFile) throws IOException
    {
        if(multipartFile.isEmpty()){
            return "false";
        }
        File dir = new File(getClass().getResource(".").getFile(),"course"+courseId);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File file = new File(getClass().getResource("./course"+courseId).getFile(), Objects.requireNonNull(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(file);
        return "0";
    }

    /**
     * 下载文件
     * @param courseId 课程id
     * @param fileName 文件名
     * @return 文件
     */
    @RequestMapping(value = "/template/download",method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> downloadATemplate(Integer courseId, String fileName)
    {
        File file = new File(getClass().getResource("./course"+courseId).getFile(),fileName);
        if(!file.exists()) return null;
        return FileExporter.export(file);
    }

    /**
     * 查看作业要求
     */
    @RequestMapping("/homework")
    public Map<String,String> getHomeworkRequirement(Integer courseId)
    {
        Jedis jedis = jedisPool.getResource();
        Map<String,String> map = new HashMap<>();
        if(!jedis.exists("course"+courseId+"isTeamwork")) return map;
        map.put("isTeamwork",jedis.get("course"+courseId+"isTeamwork"));
        Date now = new Date();
        long seconds = now.getTime()+ jedis.ttl("course"+courseId+"isTeamwork")*1000;
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Date time = new Date(seconds);
        map.put("deadline",ft.format(time));
        jedis.close();
        return map;
    }

    /**
     * 发布作业要求
     */
    @RequestMapping(value = "/homework/publish",method = RequestMethod.POST)
    public String publishHomework(Integer courseId,Boolean isTeamwork,String deadline)
    {
        Date date = DateConverter.convert2(deadline);
        Date now = new Date();
        long seconds = (date.getTime()-now.getTime())/1000;
        if(seconds<=0) return "错误，时间已过期";
        else if(seconds>=Integer.MAX_VALUE) return "日期过久，设置失败";
        Course course = courseService.getCourse(courseId);
        if(!course.getIs_team() && isTeamwork) return "非组队类型课程，作业不能设置为合作完成";
        Jedis jedis = jedisPool.getResource();
        jedis.set("course"+courseId+"isTeamwork",isTeamwork.toString());
        jedis.expire("course"+courseId+"isTeamwork",(int)seconds);
        jedis.close();
        return "0";
    }
}
