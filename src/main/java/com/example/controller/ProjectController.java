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
     * @param teacher
     * @return 课程的List 多表联查 测试通过
     */
    @RequestMapping("")
    public List<Course> myCoursesWithProjects(Integer teacher)
    {
        return courseService.queryCoursesWithProjects(teacher);
    }

    /**
     * 批量添加项目
     * @param projects 项目的List
     * @return 状态码
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
     * 修改单个项目
     * @param project Project实体类
     * @return 状态码
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String editProjects(@RequestBody Project project)
    {
        if(moduleService.getProject(project.getId()).getIs_arranged()) return "f";//已发布排课，不能修改
        moduleService.updateProject(project);
        return "0";
    }

    /**
     * 删除单个项目，并删除相应的排课
     * @param id project的id
     * @return 状态码
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public String clearProjects(Integer id)
    {
        if(moduleService.getProject(id).getIs_arranged()) return "f";//已发布排课，不能删除
        courseService.deleteProject(id);
        moduleService.deleteModules(id);
        return "0";
    }
}
