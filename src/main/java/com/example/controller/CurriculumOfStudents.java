package com.example.controller;
import com.example.config.utils.DateConverter;
import com.example.config.utils.FileExporter;
import com.example.domain.Module;
import com.example.domain.UserCourse;
import com.example.service.ICourseService;
import com.example.service.IModuleService;
import com.example.service.IUserService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    /**
     * 查询某个学生的所有module 按时间顺序
     * @param userId 学号
     */
    @RequestMapping("/all")
    public List<Module> queryModuleByUser(Long userId)
    {
        return moduleService.queryModuleByUsername(userId);
    }

    /**
     * 查询某个学生的所有module 只显示当前日期之后的记录
     * @param userId 学号
     */
    @RequestMapping("/future")
    public List<Module> queryModuleByUserAfterToday(Long userId)
    {
        return moduleService.queryModuleByUsernameAfterToday(userId);
    }

    /**
     * 显示所有课程
     * @param userId 学号
     */
    @RequestMapping("/course")
    public List<UserCourse> queryUserCourses(Long userId)
    {
        return userService.getUserCourses(userId);
    }

    /**
     * 查看课程资料文件名单
     * @param courseId 课程id
     * @return 文件名的数组
     */
    @RequestMapping(value = "/template", method = RequestMethod.GET)
    public String[] getTemplate(Integer courseId)
    {
        File dir = new File(getClass().getResource(".").getFile(), "course" + courseId);
        if (!dir.exists()) return new String[0];
        return dir.list();
    }

    /**
     * 下载文件
     * @param courseId 课程id
     * @param fileName 文件名
     * @return 文件流 content-type=application/octet-stream
     */
    @RequestMapping(value = "/template/download", method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> downloadATemplate(Integer courseId, String fileName)
    {
        File file = new File(getClass().getResource("./course" + courseId).getFile(), fileName);
        if (!file.exists()) return null;
        return FileExporter.export(file);
    }

    /**
     * 上传报告 限制为pdf文件 大小在1MB内
     * @param userId        学号
     * @param courseId      课程id
     * @param multipartFile 文件
     */
    @RequestMapping(value = "pdf/post", method = RequestMethod.POST)
    public String UploadFile(Long userId, Integer courseId, @RequestParam("file") MultipartFile multipartFile) throws IOException
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
        File dir = new File(getClass().getResource(".").getFile(), "course" + courseId + "report");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(getClass().getResource("./course" + courseId + "report").getFile(), userId + ".pdf");
        multipartFile.transferTo(file);
        jedis.close();
        return "0";
    }

    /**
     * 预览报告 返回pdf文件流 content-type=application/pdf
     * @param userId   学号
     * @param courseId 课程id
     */
    @RequestMapping("/pdf/view")
    public void showPdf(Long userId, Integer courseId, HttpServletResponse response)
    {
        response.setContentType("application/pdf");
        FileInputStream in;
        OutputStream out;
        try {
            in = new FileInputStream(new File(getClass().getResource("./course"+courseId+"report").getFile(),userId+".pdf"));
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