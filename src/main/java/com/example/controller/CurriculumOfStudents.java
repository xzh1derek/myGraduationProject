package com.example.controller;
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
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("curricula")
public class CurriculumOfStudents
{
    @Autowired
    private IModuleService moduleService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICourseService courseService;

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
    @RequestMapping(value = "/template",method = RequestMethod.GET)
    public String[] getTemplate(Integer courseId)
    {
        File dir = new File(getClass().getResource(".").getFile(),"course"+courseId);
        if(!dir.exists()) return new String[0];
        return dir.list();
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
}
