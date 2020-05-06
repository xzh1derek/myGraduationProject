package com.example.controller;

import com.example.config.redis.RedisService;
import com.example.domain.Teacher;
import com.example.domain.User;
import com.example.service.IAccountService;
import com.example.service.ITeacherService;
import com.example.service.IUserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
public class UserController
{
    @Autowired
    private IUserService userService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private ITeacherService teacherService;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private RedisService redisService;

    /**
     * 进入个人主界面
     */
    @RequestMapping("userInfo")
    public User userInfo(@RequestHeader("token") String token)
    {
        Long userId = redisService.getUserId(token);
        User user = userService.findAUser(userId);
        Jedis jedis = jedisPool.getResource();
        user.setNew_message(jedis.keys("mail"+userId+":*").size());
        jedis.close();
        return user;
    }

    /**
     * 修改qq号
     */
    @RequestMapping(value = "userInfo/updateQQ",method = RequestMethod.POST)
    public String updateQQ(@RequestHeader("token")String token, String qq)
    {
        userService.updateQQ(redisService.getUserId(token),qq);
        return "0";
    }

    /**
     * 修改密码
     * @return 状态码
     */
    @RequestMapping(value = "password/update",method = RequestMethod.POST)
    public String updatePassword(@RequestHeader("token")String token,String password)
    {
        accountService.updatePassword(redisService.getUserId(token).toString(),new Md5Hash(password).toString());
        return "0";
    }

    /**
     * 返回老师数据
     * @param username 老师的id
     */
    @RequestMapping("teacherInfo")
    public Teacher getTeacher(String username)
    {
        return teacherService.queryTeacherByUsername(username);
    }
}
