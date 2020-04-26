package com.example.controller;
import com.example.config.redis.RedisService;
import com.example.domain.Account;
import com.example.service.IAccountService;
import com.example.service.ITeacherService;
import com.example.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class Login
{
    @Autowired
    private IAccountService accountService;
    @Autowired
    private ITeacherService teacherService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 登录功能
     * @param username 账号
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Object login(String username,String password)
    {
        Jedis jedis = jedisPool.getResource();
        if(!accountService.existAccount(username)) return "账号不存在";//账号不存在
        Account account = accountService.getAccount(username);
        String psw = account.getPassword();
        if(!password.equals("123")){
            password = new Md5Hash(password).toString();
        }
        Map<String,String> map = new HashMap<>();
        Integer identity = account.getIdentity();
        String token = UUID.randomUUID().toString();
        if(identity==1){
            if(password.equals(psw)) {
                map.put("token",token);
                map.put("identity","student");
                jedis.hset(token,"id",account.getUsername());
                jedis.hset(token,"auth",account.getIdentity().toString());
            }
            else return "密码错误，请重新输入";
        }else{
            if(password.equals(psw)){
                jedis.hset(token,"id",teacherService.queryTeacherByUsername(account.getUsername()).getId().toString());
                jedis.hset(token,"auth",account.getIdentity().toString());
                map.put("token",token);
                map.put("identity","teacher");
            }
            else return "密码错误，请重新输入";
        }
        jedis.expire(token,6*3600);
        jedis.save();
        jedis.close();
        return map;
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "logout",method = RequestMethod.POST)
    public String logout(@RequestHeader("token")String token)
    {
        Jedis jedis = jedisPool.getResource();
        jedis.del(token);
        jedis.close();
        return "0";
    }
//    /**
//     * 使用shiro的登录
//     * @return
//     */
//    @RequestMapping(value = "loginShiro",method = RequestMethod.POST)
//    @ResponseBody
//    public Object loginMethod(String username, String password)
//    {
//        Jedis jedis = jedisPool.getResource();
//        Subject subject = SecurityUtils.getSubject();
//        if(!password.equals("123")){
//            password = new Md5Hash(password).toString();
//        }
//        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
//        try{
//            System.out.println(token);
//            subject.login(token);
//            Account account = accountService.getAccount(username);
//            Map<String,String> map = new HashMap<>();
//            String myToken = UUID.randomUUID().toString();
//            System.out.println(myToken);
//            map.put("token",myToken);
//            if(account.getIdentity()==1) {
//                jedis.set(myToken,account.getUsername());
//                map.put("identity","student");
//            }
//            else{
//                jedis.set(myToken,teacherService.queryTeacherByUsername(account.getUsername()).getId().toString());
//                map.put("identity","teacher");
//            }
//            jedis.expire(myToken,24*3600);
//            jedis.save();
//            return map;
//        }catch(UnknownAccountException e) {
//            e.printStackTrace();
//            return "用户不存在";//用户不存在
//        }catch(IncorrectCredentialsException e){
//            e.printStackTrace();
//            return "密码错误";//密码错误
//        }
//    }
//
//    @RequestMapping("index")
//    public Object index()
//    {
//        return SecurityUtils.getSubject().getSession();
//    }
}
