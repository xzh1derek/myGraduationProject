package com.example.controller;
import com.example.domain.Module;
import com.example.domain.Project;
import com.example.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("module")
public class ModuleController
{
    @Autowired
    private IModuleService moduleService;

    /**
     * 查询某个老师有关项目的所有子批次
     * @param teacher
     * @return Project的List 多表联查
     */
    @RequestMapping("")
    public List<Project> queryProjectWithModule(Integer teacher)
    {
        return moduleService.queryProjectWithModules(teacher);
    }

    /**
     * 添加批次
     * @param modules module的List
     * @return 状态码
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String createArbitraryModule(@RequestBody List<Module> modules)
    {
        for(Module module : modules)
        {
            moduleService.createModule(module);
        }
        return "0";
    }

}
