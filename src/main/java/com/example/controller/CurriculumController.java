package com.example.controller;

import com.example.domain.Module;
import com.example.domain.User;
import com.example.service.IModuleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("curriculum")
public class CurriculumController
{
    @Autowired
    private IModuleService moduleService;

    /**
     * 查询某个老师下所有项目下的所有批次，以及它们对应的项目以及课程
     * @return 小课的List 多表联查
     */
    @RequestMapping("/show")
    public List<Module> queryModulesWithProjectsAndCourses()
    {
        Subject subject = SecurityUtils.getSubject();
        return moduleService.queryModulesWithProjectAndCourse();
    }

    /**
     * 查询选了（或排了）某个小课的所有学生名单
     * @param moduleId 小课的序号
     * @return 学生的List
     */
    @RequestMapping("/students")
    public List<User> queryStudentsByModule(Integer moduleId)
    {
        return moduleService.queryStudentsByModule(moduleId);
    }
}
