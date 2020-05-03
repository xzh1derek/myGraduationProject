package com.example.controller;
import com.example.config.redis.RedisService;
import com.example.domain.Mail;
import com.example.domain.Team;
import com.example.service.ITeamService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
public class Apply
{
    @Autowired
    private ITeamService teamService;
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 丙申请加入某队伍
     * @param teamId 队伍编号
     * @return 返回状态码 本地测试通过
     */
    @RequestMapping(value = "apply",method = RequestMethod.POST)
    public String apply(@RequestHeader("token")String token, Integer teamId)
    {
        Jedis jedis = jedisPool.getResource();
        Team team = teamService.getTeam(teamId);
        Long userId = redisService.getUserId(token);
        if(!userService.userMatchCourse(userId,team.getCourse_id())) return "9";//Your course does not match the team's curriculum
        if(userService.hasATeam(userId,team.getCourse_id())) return "2";//You have a team
        if(!team.getAvailable()) return "3";//The team you applied is full or not available.
        if(jedis.exists("application:"+userId)) return "4";//You have an untreated application, you can't apply for another team.
        int mailId = redisService.sendMail(userId,team.getLeader(),2,teamId,userId+"申请加入队伍ID: "+teamId);
        jedis.hset("application:"+userId,"receiver",Long.toString(team.getLeader()));
        jedis.hset("application:"+userId,"mailId",Integer.toString(mailId));
        jedis.close();
        return "0";
    }

    /**
     * 我的申请
     * @return 申请邮件 本地测试通过
     */
    @RequestMapping("myApplication")
    public List<Mail> myApplication(@RequestHeader("Token")String token)
    {
        Jedis jedis = jedisPool.getResource();
        List<Mail> mails = new ArrayList<>();
        Long userId = redisService.getUserId(token);
        if(jedis.exists("application:"+userId)){
            String receiver = jedis.hget("application:"+userId,"receiver"),mailId = jedis.hget("application:"+userId,"mailId");
            Mail mail = redisService.queryMail("mail"+receiver+":"+mailId);
            mail.setReceiver(receiver);
            mails.add(mail);
        }
        jedis.close();
        return mails;
    }

    /**
     * 丙撤回申请
     * @return 状态码 本地测试通过
     */
    @RequestMapping(value = "withdraw",method = RequestMethod.POST)
    public String withdraw(@RequestHeader("Token")String token)
    {
        Jedis jedis = jedisPool.getResource();
        Long userId = redisService.getUserId(token);
        jedis.del("application:"+userId);
        jedis.close();
        return "0";
    }
}
