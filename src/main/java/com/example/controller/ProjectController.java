package com.example.controller;
import com.example.config.redis.RedisService;
import com.example.domain.Course;
import com.example.domain.Module;
import com.example.domain.Project;
import com.example.service.ICourseService;
import com.example.service.IModuleService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
    @Autowired
    private RedisService redisService;

    /**
     * 查询所有课程以及其所有子项目
     * @return 课程的List 多表联查 测试通过
     */
    @RequestMapping("")
    public List<Course> myCoursesWithProjects()
    {
        return courseService.queryCoursesWithProjects();
    }

    /**
     * 批量添加项目 事务回滚 当组队类的课排了一个固定排课的项目时被检测到 前面加的课都无效
     * @param projects 项目的List
     * @return 状态码
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @Transactional
    public String addProjects(@RequestBody Project[] projects)
    {
        Course course = courseService.getCourse(projects[0].getCourse_id());
        for(Project project : projects)
        {
            if(course.getIs_team()&&project.getIs_fixed()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return "组队类课程不支持固定排课";
            }
            moduleService.createProject(project);
        }
        return "0";
    }

    /**
     * 修改单个项目
     * @param project Project实体类
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String editProject(@RequestBody Project project)
    {
        if(moduleService.getProject(project.getId()).getIs_published()&&project.getIs_fixed()!=moduleService.getProject(project.getId()).getIs_fixed()) return "已发布排课，不能修改排课类型";
        if(courseService.getCourse(project.getCourse_id()).getIs_team()&&project.getIs_fixed()) return "组队类课程只支持任选排课";
        moduleService.updateProject(project);
        return "0";
    }

    /**
     * 删除单个项目，并删除相应的排课
     * @param id project的id
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public String clearProject(Integer id)
    {
        if(moduleService.getProject(id).getIs_published()) return "已发布排课，不能删除";
        courseService.deleteProject(id);
        moduleService.deleteModules(id);
        return "0";
    }

    /**
     * 管理员发布排课 (撤销排课或者redis重启后都要重新发布 区别在于 撤销后已经选了的记录不消失 而redis清空后就消失了需要重新选课)
     * @param id project的id
     * @return 状态信息
     */
    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    @Transactional
    public String publishProject(Integer id)
    {
        Project project = moduleService.getProject(id);
        Course course = courseService.getCourse(project.getCourse_id());
        if(!course.getIs_published()) return "所属课程还未发布，无法发布排课，请先发布课程";
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
        else //任选排课，还需要管理员设置选课的截止时间，传到redis里
        {
            Integer stuNums = course.getStu_num(),nums=0;
            for(Module module : modules){
                nums+=module.getStu_num();
            }
            if(nums<=stuNums) return "排课被驳回，排课各批次人数和少于该课程的总人数";
            //redis里放入course
            redisService.insertCourseIntoRedis(course);
            //redis里放入project
            redisService.insertProjectIntoRedis(project);
            //redis里放入module
            for(Module module : modules){
                redisService.insertModuleIntoRedis(module);
            }
        }
        moduleService.updateIsPublished(id,true);
        return "0";
    }
}
