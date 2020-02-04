package com.example.controller;
import com.example.config.redis.RedisService;
import com.example.domain.Course;
import com.example.domain.Module;
import com.example.domain.Project;
import com.example.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("elective")
public class ElectiveController //备选课管理
{
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IModuleService moduleService;

    /**
     * 查询所有学生当前可选的projects 以及相关的course和module
     * @return project的list
     */
    @RequestMapping("")
    public List<Project> queryAllElectives()
    {
        Jedis jedis = jedisPool.getResource();
        Set<String> courseKeys = jedis.keys("course:*");
        List<Project> projects = new ArrayList<>();
        for(String courseKey : courseKeys)
        {
            Course course = redisService.queryCourseFromRedis(courseKey);
            Set<String> projectKeys = jedis.keys("course"+course.getId()+"project:*");
            for(String projectKey : projectKeys)
            {
                Project project = redisService.queryProjectFromRedis(projectKey);
                project.setCourse(course);
                List<Module> modules = new ArrayList<>();
                Set<String> moduleKeys = jedis.keys("project"+project.getId()+"module:*");
                for(String moduleKey : moduleKeys)
                {
                    Module module = redisService.queryModuleFromRedis(moduleKey);
                    modules.add(module);
                }
                project.setModules(modules);
                projects.add(project);
            }
        }
        jedis.close();
        return projects;
    }

    /**
     * 撤销选课发布 redis里删掉project以及其下modules 不影响已经选课的人 如果是最后一个还要删掉course
     * @param projectId project的id
     */
    @RequestMapping(value = "/withdraw",method = RequestMethod.POST)
    @Transactional
    public String withdrawElectives(Integer projectId)
    {
        Jedis jedis = jedisPool.getResource();
        Integer courseId = redisService.queryCourseIdByProjectId(projectId);
        jedis.del("course"+courseId+"project:"+projectId);
        jedis.del("projectId:"+projectId);
        Set<String> keys = jedis.keys("project"+projectId+"module:*");
        for(String key : keys){
            jedis.del("moduleId:"+jedis.hget(key,"id"));
            jedis.del(key);
        }
        Set<String> ks = jedis.keys("course"+courseId+"project:*");
        if(ks.isEmpty()){
            jedis.del("course:"+courseId);
        }
        jedis.close();
        return "撤销成功。重新发布时，请到【排课管理】中再次发布排课。";
    }

    /**
     * 选择project 追加一个新的批次当人数不够时 在mysql和redis里分别加入
     * @param module module实体
     */
    @RequestMapping(value = "/add/module",method = RequestMethod.POST)
    public String addAModuleToProject(@RequestBody Module module)
    {
        if(module.getProject_id()==null) return "f";//一定要放入project_id进去
        Integer moduleId = moduleService.createModule(module);
        module.setId(moduleId);
        redisService.insertModuleIntoRedis(module);
        return "0";
    }

    /**
     * 一键删除所有过期批次 以及user_module的相关数据
     */
    @RequestMapping(value = "/delete/expired",method = RequestMethod.DELETE)
    public String deleteExpiredModules()
    {
        Jedis jedis = jedisPool.getResource();
        List<Module> modules = moduleService.queryModuleExpired();
        for(Module module : modules)
        {
            moduleService.deleteUserModuleByModule(module.getId());
            jedis.del("project"+module.getProject_id()+"module:"+module.getId());
            jedis.del("moduleId:"+module.getId());
            moduleService.deleteModule(module.getId());
        }
        return "0";
    }
}
