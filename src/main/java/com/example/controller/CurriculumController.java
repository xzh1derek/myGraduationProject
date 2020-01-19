package com.example.controller;
import com.example.config.utils.ByteConverter;
import com.example.domain.Module;
import com.example.domain.User;
import com.example.service.IModuleService;
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
     * 查询某个老师下所有项目下的所有已发布批次的课表，按时间顺序
     * @return 小课的List 多表联查
     */
    @RequestMapping("/show")
    public List<Module> queryModulesWithProjectsAndCourses(Integer teacherId)
    {
        Long teacher = ByteConverter.convertIndexToLong(new Integer[]{teacherId});
        return moduleService.queryModuleOrderByDate(teacher);
    }

    /**
     * 查询某个老师下所有项目下的所有已发布批次的课表，按时间顺序，只显示当前日期之后的所有记录
     * @return 小课的List 多表联查
     */
    @RequestMapping("/future")
    public List<Module> queryModulesWithProjectsAndCoursesAfterToday(Integer teacherId)
    {
        Long teacher = ByteConverter.convertIndexToLong(new Integer[]{teacherId});
        return moduleService.queryModuleAfterToday(teacher);
    }

    /**
     * 查询选了（或排了）某个批次的所有学生名单
     * @param moduleId 小课的序号
     * @return 学生的List
     */
    @RequestMapping("/students")
    public List<User> queryStudentsByModule(Integer moduleId)
    {
        return moduleService.queryStudentsByModule(moduleId);
    }
}
