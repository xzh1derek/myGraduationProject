package com.example.controller;
import com.example.config.redis.RedisService;
import com.example.config.utils.DateConverter;
import com.example.config.utils.FileExporter;
import com.example.domain.Module;
import com.example.domain.UserCourse;
import com.example.service.IModuleService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("curricula")
public class CurriculumOfStudents
{
    @Autowired
    private IModuleService moduleService;
    @Autowired
    private IUserService userService;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private RedisService redisService;

    /**
     * 查询某个学生的所有module 按时间顺序
     */
    @RequestMapping("/all")
    public List<Module> queryModuleByUser(@RequestHeader("Token")String token)
    {
        Long userId = redisService.getUserId(token);
        return moduleService.queryModuleByUsername(userId);
    }

    /**
     * 查询某个学生的所有module 只显示当前日期之后的记录
     */
    @RequestMapping("/future")
    public List<Module> queryModuleByUserAfterToday(@RequestHeader("Token")String token)
    {
        Long userId = redisService.getUserId(token);
        return moduleService.queryModuleByUsernameAfterToday(userId);
    }

    /**
     * 显示所有课程
     */
    @RequestMapping("/course")
    public List<UserCourse> queryUserCourses(@RequestHeader("Token")String token)
    {
        Long userId = redisService.getUserId(token);
        return userService.getUserCourses(userId);
    }

    /**
     * 下载文件
     * @param courseId 课程id
     * @return 文件流 content-type=application/octet-stream
     */
    @RequestMapping(value = "/template/download", method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> downloadATemplate(Integer courseId)
    {
        File dir = new File(getClass().getResource(".").getFile(),"course"+courseId);
        if(!dir.exists()) return null;
        File[] files = dir.listFiles();
        if(files==null||files.length==0) return null;
        return FileExporter.export(files[0]);
    }

    /**
     * 上传报告 限制为pdf文件 大小在1MB内
     * @param courseId      课程id
     * @param multipartFile 文件
     */
    @RequestMapping(value = "pdf/post", method = RequestMethod.POST)
    public String UploadFile(@RequestHeader("Token")String token, Integer courseId, @RequestParam("file") MultipartFile multipartFile) throws IOException
    {
        Jedis jedis = jedisPool.getResource();
        if (!jedis.exists("course" + courseId + "requirement")) {
            return "还未发布报告要求";
        }
        Boolean isTeamwork = Boolean.parseBoolean(jedis.hget("course" + courseId + "requirement", "isTeamwork"));
        Date deadline = DateConverter.convert2(jedis.hget("course" + courseId + "requirement", "deadline"));
        if (deadline.before(new Date())) {
            return "已过截止时间";
        }
        Long userId = redisService.getUserId(token);
        UserCourse userCourse = userService.queryUserCourseWithCourse(userId, courseId);
        if (userCourse.getCourse().getIs_team() && !userCourse.getIs_leader() && isTeamwork) {
            return "小组合作完成报告，只需队长上传即可";
        }
        if (multipartFile.isEmpty()) {
            return "f";
        } else if (multipartFile.getSize() > 1024 * 1024) {
            return "超出大小限制";
        } else if (!Objects.equals(multipartFile.getContentType(), "application/pdf")) {
            return "格式错误，请上传pdf文件";
        }
        File dir = new File(getClass().getResource(".").getFile(), "course_" + courseId + "_report");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(getClass().getResource("./course_" + courseId + "_report").getFile(), userId + ".pdf");
        multipartFile.transferTo(file);
        jedis.close();
        return "0";
    }

    /**
     * 预览报告 返回pdf文件流 content-type=application/pdf
     * @param courseId 课程id
     */
    @RequestMapping("/pdf/view")
    public void showPdf(@RequestHeader("Token")String token, Integer courseId, HttpServletResponse response)
    {
        response.setContentType("application/pdf");
        FileInputStream in;
        OutputStream out;
        Long userId = redisService.getUserId(token);
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
}