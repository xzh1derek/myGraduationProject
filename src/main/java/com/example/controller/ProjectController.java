package com.example.controller;

import com.example.domain.Course;
import com.example.domain.Module;
import com.example.domain.Project;
import com.example.domain.UserCourse;
import com.example.service.ICourseService;
import com.example.service.IModuleService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("project")
public class ProjectController
{
    @Autowired
    private IModuleService moduleService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IUserService userService;

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
        if(moduleService.getProject(project.getId()).getIs_published()) return "f";//已发布排课，不能修改
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
        if(moduleService.getProject(id).getIs_published()) return "f";//已发布排课，不能删除
        courseService.deleteProject(id);
        moduleService.deleteModules(id);
        return "0";
    }

    /**
     * 管理员发布排课
     * @param id project的id
     * @return 状态信息
     */
    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    public String publishProject(Integer id)
    {
        Project project = moduleService.getProject(id);
        Course course = courseService.getCourse(project.getCourse_id());
        List<Module> modules = moduleService.queryModulesByProject(id);
        if(project.getIs_fixed())//固定排课，将绑定的班级里的学生与module绑定
        {
            //检查固定排课是否有遗漏、重复、错误
            List<Integer> classes = courseService.queryClassByCourse(course.getId());
            HashSet<Integer> classesSet = new HashSet<>(classes);
            for(Module module : modules){
                Integer class1 = module.getClass1(), class2 = module.getClass2();
                if(class1==null&&class2==null) return "提交被驳回，批次["+module.getTime()+"-"+module.getLocation()+"]未分配班级";
                if(class1!=null){
                    if(classesSet.contains(class1)) {
                        classesSet.remove(class1);
                    }else{
                        return "提交被驳回，班级"+class1+"重复排课或不在课程计划内";
                    }
                }
                if(class2!=null){
                    if(classesSet.contains(class2)) {
                        classesSet.remove(class2);
                    }else{
                        return "提交被驳回，班级"+class2+"重复排课或不在课程计划内";
                    }
                }
            }
            if(!classesSet.isEmpty()){
                return "提交被驳回，班级"+classesSet.toString()+"未被分配批次";
            }
            //固定排课给module绑定学生
            for(Module module : modules)
            {
                Integer class1 = module.getClass1(),class2 = module.getClass2();
                List<Long> usernameList = userService.findUsersByClass(class1);
                if(class2!=null) usernameList.addAll(userService.findUsersByClass(class2));
                for(Long username : usernameList){
                    moduleService.newUserModule(username,module.getId());
                }
                moduleService.updateStuNum(module.getId(),usernameList.size());
            }
        }
        else //任选排课，将module传到redis里
        {
            Integer stuNums = course.getStu_num(),nums=0;
            for(Module module : modules){
                nums+=module.getStu_num();
            }
            if(nums<=stuNums) return "排课被驳回，排课各批次人数和少于该课程的总人数";
            System.out.println("任选排课");
        }
        moduleService.updateIsPublished(id,true);
        return "0";
    }
}
