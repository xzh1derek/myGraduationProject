package com.example.controller;
import com.example.config.redis.RedisService;
import com.example.domain.*;
import com.example.domain.Module;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

@RestController
@RequestMapping("choosing")
public class ChoosingModule
{
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IUserService userService;

    /**
     * 返回所有可选课程
     */
    @RequestMapping("/course")
    public Object courseToChoose()
    {
        Jedis jedis = jedisPool.getResource();
        Set<String> keys = jedis.keys("course:*");
        if(keys.isEmpty()) return "n";//没有待选的课程
        List<Course> courseList = new ArrayList<>();
        for(String key : keys){
            Course course = redisService.queryCourseFromRedis(key);
            courseList.add(course);
        }
        jedis.close();
        return courseList;
    }

    /**
     * 返回课程下所有待选project 标明已选或者未选 事务回滚
     * @param userId 学号
     * @param courseId 课程id
     */
    @RequestMapping(value = "/project",method = RequestMethod.POST)
    @Transactional
    public Object projectToChoose(Long userId, Integer courseId)
    {
        Jedis jedis = jedisPool.getResource();
        String k = "course"+courseId+"user:"+userId;
        if(!jedis.exists(k)){
            if(!userService.userMatchCourse(userId,courseId)) return "你没有该课程计划";
            else{
                redisService.insertUserCourseIntoRedis(userId,courseId);
            }
        }
        boolean isTeam = Boolean.parseBoolean(jedis.hget(k,"isTeam")),
                isLeader = Boolean.parseBoolean(jedis.hget(k,"isLeader"));
        if(isTeam){
            if(!jedis.hexists(k,"teamStuNum")) {
                jedis.del(k);
                return "该课程你还未组队，请尽快组队";
            }
            if(!isLeader){
                jedis.del(k);
                return "你不是队长，无权操作";
            }
        }
        List<Project> projects = new ArrayList<>();
        Set<String> keys = jedis.keys("course"+courseId+"project:*");
        for(String key : keys){
            Project project = redisService.queryProjectFromRedis(key);
            if(jedis.exists("project"+project.getId()+"user:"+userId)){//显示用户是否已经选过这个project
                project.setIs_chosen(true);
            }else{
                project.setIs_chosen(false);
            }
            projects.add(project);
        }
        jedis.close();
        return projects;
    }

    /**
     * 如果project未选 则进入选课页面
     * @param userId 学号
     * @param projectId project的id
     * @return project已选时返回"f"，未选时返回其下的所有module的List
     */
    @RequestMapping(value = "/module/all",method = RequestMethod.POST)
    public Object moduleAll(Long userId, Integer projectId)
    {
        List<Module> modules = new ArrayList<>();
        Jedis jedis = jedisPool.getResource();
        if(jedis.exists("project"+projectId+"user:"+userId)){
            return "f";//已选过 不能进入
        }
        Set<String> keys = jedis.keys("project"+projectId+"module:*");
        for(String key : keys){
            Module module = redisService.queryModuleFromRedis(key);
            modules.add(module);
        }
        jedis.close();
        return modules;
    }

    /**
     * 如果project已选 则查看选课信息 （还可以同时显示在选课界面下面的“我的选课”区域）
     * @param userId 学号
     * @param projectId project的id
     * @return project未选时返回"n"，已选时返回module实体+是否已处理字段
     */
    @RequestMapping(value = "/module/my",method = RequestMethod.POST)
    public Module myModule(Long userId, Integer projectId)
    {
        Jedis jedis = jedisPool.getResource();
        String key = "project"+projectId+"user:"+userId;
        if(!jedis.exists(key)) return null; //project还未选 返回空
        int moduleId = Integer.parseInt(jedis.get(key));
        key = "project"+projectId+"module:"+moduleId;
        Module module = redisService.queryModuleFromRedis(key);
        jedis.close();
        return module;
    }

    /**
     * 选择某个批次 加过了不能再加 人数满时无法加入（组队时批次人数能稍微多出一点） 事务回滚
     * @param userId 学号
     * @param moduleId module的id
     */
    @RequestMapping(value = "/module/choose",method = RequestMethod.POST)
    @Transactional
    public String moduleChoose(Long userId, Integer moduleId)
    {
        Jedis jedis = jedisPool.getResource();
        Integer projectId = redisService.queryProjectIdByModuleId(moduleId);
        String key = "project"+projectId+"module:"+moduleId;
        Module module = redisService.queryModuleFromRedis(key);
        if(module.getCur_num() >= module.getStu_num()){
            return "人数已满，无法选择";
        }
        Integer courseId = redisService.queryCourseIdByProjectId(projectId);
        key = "project"+projectId+"user:"+userId;
        if(jedis.exists(key)){
            return "已预约，无法重复选择";
        }
        jedis.set(key,module.getId().toString());
        //选课成功，下面开始添加进redis
        key = "course"+courseId+"user:"+userId;
        if(!jedis.exists(key)){
            redisService.insertUserCourseIntoRedis(userId,courseId);
        }
        boolean isTeam = Boolean.parseBoolean(jedis.hget(key,"isTeam"));
        int teamStuNum = Integer.parseInt(jedis.hget(key,"teamStuNum")),
                teamId = Integer.parseInt(jedis.hget(key,"teamId"));
        if(isTeam){
            key = "studentsOfModule:"+moduleId; // key   studentsOfModule:[moduleId]
            jedis.sadd(key,userId.toString());  // value 学号的Set集合
            List<Long> usernameList = userService.queryUsernameByTeamId(teamId);
            for(Long username : usernameList){
                jedis.sadd(key,username.toString());
            }
            key = "project"+projectId+"module:"+moduleId;
            jedis.hincrBy(key,"curNum",teamStuNum);
        }else{
            key = "project"+projectId+"module:"+moduleId;
            jedis.hincrBy(key,"curNum",1);
        }
        jedis.close();
        return "0";
    }

}
