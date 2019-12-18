package com.example.controller;

import com.example.domain.Course;
import com.example.domain.Project;
import com.example.service.ICourseService;
import com.example.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("project")
public class ProjectController
{
    @Autowired
    private IModuleService moduleService;
    @Autowired
    private ICourseService courseService;

    /**
     * 查询某个老师下所有课程的所有项目
     * @param teacher 老师
     * @return 课程的List 多表联查 测试通过
     */
    @RequestMapping("")
    public List<Course> myCoursesWithProjects(Integer teacher)
    {
        return courseService.queryCoursesWithProjects(teacher);
    }

    /**
     * 添加项目
     * @param projects project实体List
     * @return 状态码 测试通过
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String addProjects(@RequestBody Project[] projects)
    {
        for(Project project : projects)
        {
            moduleService.createProject(project);
        }
        return "0";
    }

    /**
     * 更改项目
     * @param courseId 课程id
     * @param projects project实体List
     * @return 状态码
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String updateProjects(Integer courseId, @RequestBody Project[] projects)
    {
        courseService.deleteProjects(courseId);
        for(Project project : projects)
        {
            moduleService.createProject(project);
        }
        return "0";
    }
}
