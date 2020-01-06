package com.example.controller;
import com.example.domain.*;
import com.example.domain.Module;
import com.example.service.ICourseService;
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
    @Autowired
    private ICourseService courseService;

    /**
     * 查询某个老师有关项目的所有子批次
     * @param teacher
     * @return Project的List 多表联查 测试通过
     */
    @RequestMapping("")
    public List<Project> queryProjectWithModule(Integer teacher)
    {
        return moduleService.queryProjectWithModules(teacher);
    }

    /**
     * 批量添加批次
     * @param modules module的List
     * @return 状态码
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String createModule(@RequestBody List<Module> modules)
    {
        if(moduleService.getProject(modules.get(0).getProject_id()).getIs_published()) return "f";//已发布排课，不能添加批次
        for(Module module : modules)
        {
            moduleService.createModule(module);
        }
        return "0";
    }

    /**
     * 修改批次
     * @param module module实体类
     * @return 状态码 测试通过
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String updateModule(@RequestBody Module module)
    {
        if(moduleService.getProject(module.getProject_id()).getIs_published()) return "f";//已发布排课，不能修改批次
        moduleService.updateModule(module);
        return "0";
    }

    /**
     * 删除批次
     * @param id module的id
     * @return 状态码 测试通过
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public String deleteModule(Integer id)
    {
        if(moduleService.getProject(moduleService.getModule(id).getProject_id()).getIs_published()) return "f";//已发布排课，不能删除批次
        moduleService.deleteModule(id);
        return "0";
    }

    /**
     * 返回待绑定班级的List
     * @param id module的id
     * @return schoolWithClass的List
     */
    @RequestMapping(value = "/classes",method = RequestMethod.GET)
    public List<School> beforeBind(Integer id)
    {
        Integer courseId = moduleService.getProject(moduleService.getModule(id).getProject_id()).getCourse_id();
        return courseService.querySchoolWithClassesLimited(courseId);
    }

    /**
     * 固定排课下给批次绑定班级，最多两个班级
     * @param id module的id
     * @param classes 班级的List
     * @return 状态码
     */
    @RequestMapping(value = "/bind",method = RequestMethod.POST)
    public String bindClasses(Integer id,@RequestBody Integer[] classes)
    {
        if(classes.length>2) return "f";
        else if(classes.length==1){
            moduleService.addTwoClassesIntoModule(id,classes[0],null);
        }
        else if(classes.length==2){
            moduleService.addTwoClassesIntoModule(id,classes[0],classes[1]);
        }
        else{
            moduleService.addTwoClassesIntoModule(id,null,null);
        }
        return "0";
    }

    /**
     * 查询某个老师下所有项目下的所有批次，以及它们对应的项目以及课程
     * @param teacher
     * @return 小课的List 多表联查
     */
    @RequestMapping("/show")
    public List<Module> queryModuleWithStudents(Integer teacher)
    {
        return moduleService.queryModulesByTeacher(teacher);
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
