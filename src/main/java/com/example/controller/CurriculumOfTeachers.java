package com.example.controller;
import com.example.config.utils.ByteConverter;
import com.example.domain.*;
import com.example.domain.Module;
import com.example.service.ICourseService;
import com.example.service.IMailService;
import com.example.service.IModuleService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

@RestController
@RequestMapping("curriculum")
public class CurriculumOfTeachers //老师课表 处理选课
{
    @Autowired
    private IModuleService moduleService;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IMailService mailService;

    /**
     * 查询某个老师下所有项目下的所有已发布批次的课表，按时间顺序
     * @return 小课的List 多表联查
     */
    @RequestMapping("/all")
    public List<Module> queryModulesWithProjectsAndCourses(Integer teacherId)
    {
        Long teacher = ByteConverter.convertIndexToLong(new Integer[]{teacherId});
        return moduleService.queryModuleOrderByDate(teacher);
    }

    /**
     * 查询某个老师下所有项目下的所有已发布批次的课表，只显示当前日期之后的所有记录
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

    /**
     * 查看某老师下所有未处理的选课信息
     * @param teacherId 老师的id
     * @return Module的List 注入UserModules并带上User实体
     */
    @RequestMapping("/module")
    public List<Module> queryUserModuleUnhandled(Integer teacherId)
    {
        Jedis jedis = jedisPool.getResource();
        List<Module> modules = moduleService.queryModuleOfArbitrary(ByteConverter.convertIndexToLong(new Integer[]{teacherId}));
        List<Module> moduleList = new ArrayList<>();
        for(Module module : modules)
        {
            String key = "studentsOfModule:"+module.getId();
            Set<String> usernameList = jedis.smembers(key);
            if(usernameList.isEmpty()) {
                continue;
            }
            List<UserModule> userModules = new ArrayList<>();
            for(String username : usernameList)
            {
                UserModule userModule = new UserModule();
                userModule.setUser(userService.findAUser(Long.parseLong(username)));
                userModules.add(userModule);
            }
            module.setUserModules(userModules);
            moduleList.add(module);
        }
        jedis.close();
        return moduleList;
    }

    /**
     * 批量处理选课 一次可处理多个学生 但只能选择一个module
     * @param moduleId module的id
     * @param userIds userId的数组
     */
    @RequestMapping(value = "/module/dispose",method = RequestMethod.POST)
    @Transactional
    public String disposeUserModule(Integer moduleId, @RequestBody Long[] userIds)
    {
        Jedis jedis = jedisPool.getResource();
        Module module = moduleService.getModule(moduleId);
        Project project = moduleService.getProject(module.getProject_id());
        Course course = courseService.getCourse(project.getCourse_id());
        for(Long userId : userIds)
        {
            jedis.srem("studentsOfModule:"+moduleId,userId.toString());
            moduleService.newUserModule(userId,moduleId);
            String text = "预约批次成功。课程【"+course.getCourse_name()+"】新增["+project.getProject_name()+"]，时间["+
                    module.getDate()+" "+module.getTime()+"]，地点["+module.getLocation()+"]。请到【课程管理】->【我的课表】中查看。";
            mailService.sendMail(0L,userId,0,null,text);
        }
        jedis.close();
        return "0";
    }
}