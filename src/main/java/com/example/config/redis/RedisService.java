package com.example.config.redis;
import com.example.config.utils.ByteConverter;
import com.example.config.utils.DateConverter;
import com.example.dao.IModuleDao;
import com.example.dao.ITeacherDao;
import com.example.dao.ITeamDao;
import com.example.dao.IUserDao;
import com.example.domain.*;
import com.example.domain.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

@Component
public class RedisService
{
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private ITeacherDao teacherDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private ITeamDao teamDao;
    @Autowired
    private IModuleDao moduleDao;

    /**key:
     * course:[id] Hash类型
     */
    public void insertCourseIntoRedis(Course course)
    {
        Jedis jedis = jedisPool.getResource();
        //redis里放入course
        String key = "course:"+course.getId();
        Map<String,String> map = new HashMap<>();
        map.put("id",course.getId().toString());
        map.put("name",course.getCourse_name());
        map.put("is_team",course.getIs_team().toString());
        map.put("teacher",course.getTeacher().toString());
        jedis.hmset(key,map);
        jedis.save();
        jedis.close();
    }

    public Course queryCourseFromRedis(String key)
    {
        Jedis jedis = jedisPool.getResource();
        Course course = new Course();
        Map<String,String> map = jedis.hgetAll(key);
        course.setId(Integer.parseInt(map.get("id")));
        course.setCourse_name(map.get("name"));
        course.setIs_team(Boolean.parseBoolean(map.get("is_team")));
        course.setTeachers(queryTeachersFromRedis(Long.parseLong(map.get("teacher"))));
        jedis.close();
        return course;
    }

    /**key:
     * course[course_id]project:[id] Hash类型
     * projectId:[id] String类型
     */
    public void insertProjectIntoRedis(Project project)
    {
        Jedis jedis = jedisPool.getResource();
        String key = "course"+project.getCourse_id()+"project:"+project.getId();
        Map<String,String> projectMap = new HashMap<>();
        projectMap.put("id",project.getId().toString());
        projectMap.put("name",project.getProject_name());
        jedis.hmset(key,projectMap);
        jedis.set("projectId:"+project.getId(),project.getCourse_id().toString());
        jedis.save();
        jedis.close();
    }

    public Project queryProjectFromRedis(String key)
    {
        Jedis jedis = jedisPool.getResource();
        Project project = new Project();
        project.setId(Integer.parseInt(jedis.hget(key,"id")));
        project.setProject_name(jedis.hget(key,"name"));
        jedis.close();
        return project;
    }

    public Integer queryCourseIdByProjectId(Integer projectId)
    {
        Jedis jedis = jedisPool.getResource();
        Integer courseId = Integer.parseInt(jedis.get("projectId:"+projectId));
        jedis.close();
        return courseId;
    }

    /**key:
     * project[project_id]module:[id] Hash类型
     * moduleId:[id] String类型
     */
    public void insertModuleIntoRedis(Module module)
    {
        Jedis jedis = jedisPool.getResource();
        String key = "project"+module.getProject_id()+"module:"+module.getId();
        Map<String,String> moduleMap = new HashMap<>();
        moduleMap.put("id",module.getId().toString());
        moduleMap.put("location",module.getLocation());
        moduleMap.put("date",module.getDate().toString());
        moduleMap.put("time",module.getTime());
        moduleMap.put("stuNum",module.getStu_num().toString());
        moduleMap.put("index",module.getModule_index().toString());
        Long mysqlNum = moduleDao.countUserNumbersByModule(module.getId()).longValue(),
             redisNum = jedis.scard("studentsOfModule:"+module.getId());
        moduleMap.put("curNum",String.valueOf(mysqlNum+redisNum));
        jedis.hmset(key,moduleMap);
        jedis.set("moduleId:"+module.getId(),module.getProject_id().toString());
        jedis.save();
        jedis.close();
    }

    public Module queryModuleFromRedis(String key)
    {
        Jedis jedis = jedisPool.getResource();
        Module module = new Module();
        module.setId(Integer.parseInt(jedis.hget(key,"id")));
        module.setLocation(jedis.hget(key,"location"));
        module.setDate(DateConverter.convert(jedis.hget(key,"date")));
        module.setTime(jedis.hget(key,"time"));
        module.setStu_num(Integer.parseInt(jedis.hget(key,"stuNum")));
        module.setCur_num(Integer.parseInt(jedis.hget(key,"curNum")));
        module.setModule_index(Integer.parseInt(jedis.hget(key,"index")));
        jedis.close();
        return module;
    }

    public Integer queryProjectIdByModuleId(Integer moduleId)
    {
        Jedis jedis = jedisPool.getResource();
        Integer projectId = Integer.parseInt(jedis.get("moduleId:"+moduleId));
        jedis.close();
        return projectId;
    }

    public void insertUserCourseIntoRedis(Long userId,Integer courseId)
    {
        Jedis jedis = jedisPool.getResource();
        String key = "course"+courseId+"user:"+userId;
        UserCourse userCourse = userDao.queryUserCourseWithCourse(userId,courseId);
        Map<String,String> map = new HashMap<>();
        map.put("isTeam",userCourse.getCourse().getIs_team().toString());
        map.put("teamId",userCourse.getTeam_id().toString());
        map.put("isLeader",userCourse.getIs_leader().toString());
        if(userCourse.getTeam_id()!=0){
            Team team = teamDao.getTeam(userCourse.getTeam_id());
            map.put("teamStuNum",team.getCurrent_num().toString());
        }
        jedis.hmset(key,map);
        jedis.expire(key,600);//定时 给key定10分钟
        jedis.close();
    }

    private List<String> queryTeachersFromRedis(Long teachers)
    {
        Jedis jedis = jedisPool.getResource();
        List<Integer> teacherIds = ByteConverter.convertLongToIndex(teachers);
        List<String> teacherNames = new ArrayList<>();
        for(Integer teacherId : teacherIds)
        {
            String key = "teacher:"+teacherId, teacher;
            if(jedis.exists(key)){
                teacher = jedis.get(key);
            }else{
                teacher = teacherDao.getTeacher(teacherId).getName();
                jedis.set(key,teacher);
            }
            teacherNames.add(teacher);
        }
        jedis.close();
        return teacherNames;
    }

    public Mail queryMail(String key){
        Jedis jedis = jedisPool.getResource();
        Map<String,String> map = jedis.hgetAll(key);
        Mail mail = new Mail();
        mail.setId(Integer.parseInt(map.get("id")));
        mail.setSender(Long.parseLong(map.get("sender")));
        mail.setType(Integer.parseInt(map.get("type")));
        mail.setTeamId(Integer.parseInt(map.get("teamId")));
        mail.setText(map.get("text"));
        jedis.close();
        return mail;
    }

    public int sendMail(Long sender,Long receiver,Integer type,Integer teamId,String text){
        Jedis jedis = jedisPool.getResource();
        int id = new Random().nextInt();
        String key = "mail"+receiver+":"+id;
        jedis.hset(key,"id", Integer.toString(id));
        jedis.hset(key,"sender",sender.toString());
        jedis.hset(key,"type",type.toString());
        jedis.hset(key,"teamId",teamId.toString());
        jedis.hset(key,"text",text);
        jedis.save();
        jedis.close();
        return id;
    }

    public Long getUserId(String token){
        Jedis jedis = jedisPool.getResource();
        String id = jedis.hget(token,"id");
        jedis.close();
        return Long.parseLong(id);
    }

    public Integer getTeacherId(String token){
        Jedis jedis = jedisPool.getResource();
        String id = jedis.hget(token,"id");
        jedis.close();
        return Integer.parseInt(id);
    }
}
