package com.example.controller;
import com.example.domain.Module;
import com.example.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("curricula")
public class CurriculumOfStudents
{
    @Autowired
    private IModuleService moduleService;

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
}
