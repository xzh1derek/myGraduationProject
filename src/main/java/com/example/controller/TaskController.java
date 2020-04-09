package com.example.controller;

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
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
}
