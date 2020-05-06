package com.example.controller;
import com.example.config.redis.RedisService;
import com.example.domain.Mail;
import com.example.domain.Team;
import com.example.domain.User;
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
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("mail")
public class MailController
{
    @Autowired
    private IUserService userService;
    @Autowired
    private ITeamService teamService;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private RedisService redisService;

    /**
     * 甲进入消息界面
     */
    @RequestMapping("")
    public List<Mail> showMails(@RequestHeader("token")String token)
    {
        Long userId = redisService.getUserId(token);
        Jedis jedis = jedisPool.getResource();
        List<Mail> mails = new ArrayList<>();
        Set<String> keys = jedis.keys("mail"+userId+":*");
        if(!keys.isEmpty()){
            for(String key : keys){
                Mail mail = redisService.queryMail(key);
                mails.add(mail);
            }
        }
        jedis.close();
        return mails;
    }

    /**
     * 申请者详细信息
     * @param sender 学号
     * @return 本地测试通过
     */
    @RequestMapping("/userDetail")
    public User showUserDetail(Long sender)
    {
        return userService.findAUser(sender);
    }

    /**
     * 邀请队伍详细信息
     * @param teamId 队伍编号
     * @return 本地测试通过
     */
    @RequestMapping("/teamDetail")
    public Team showTeamDetail(Integer teamId)
    {
        return teamService.queryTeamById(teamId);
    }

    /**
     * 同意申请/邀请
     * @param mailId 消息编号
     * @return 状态字符串
     */
    @RequestMapping("/approve")
    public String approve(@RequestHeader("token")String token,Integer mailId)
    {
        Long userId = redisService.getUserId(token);
        Jedis jedis = jedisPool.getResource();
        String key = "mail"+userId+":"+mailId;
        Mail myMail = redisService.queryMail(key);
        if(myMail.getType()==1)//如果为邀请
        {
            Integer teamId = myMail.getTeamId();
            Team team = teamService.getTeam(teamId);
            if(userService.hasATeam(userId,team.getCourse_id())) return "2";//You have a team
            if(!team.getAvailable()) return "3";//The team you applied is full or not available
            teamService.addAMember(teamId,userId);
            userService.updateTeamId(userId,team.getCourse_id(),teamId);
            jedis.del(key);
            redisService.sendMail(0L,team.getLeader(),0,0,"【系统消息】邀请被通过，成员 "+ userId +" 已入队");//发一个系统邮件，告知队长入队成功
        }
        else if(myMail.getType()==2)//如果为申请
        {
            Integer teamId = myMail.getTeamId();
            Team team = teamService.getTeam(teamId);
            if(!team.getAvailable()) return "5";//Your team is full or not available
            if(team.getCurrent_num().equals(team.getMax_num())) return "7";//Your team is full
            if(userService.hasATeam(userId,team.getCourse_id())) return "6";//He has a team
            teamService.addAMember(teamId,userId);
            userService.updateTeamId(userId,team.getCourse_id(),teamId);
            jedis.del(key);
            jedis.del("application:"+myMail.getSender().toString());
            redisService.sendMail(0L,userId,0,0,"【系统消息】队伍申请已通过，你已入队");//发送系统邮件告知申请通过
        }
        jedis.close();
        return "0";
    }

    /**
     * 拒绝邀请/申请
     * @param mailId 消息编号
     * @return 状态字符串
     */
    @RequestMapping("/reject")
    public String reject(@RequestHeader("token")String token,Integer mailId)
    {
        Long userId = redisService.getUserId(token);
        Jedis jedis = jedisPool.getResource();
        String key = "mail"+userId+":"+mailId;
        Mail myMail = redisService.queryMail(key);
        jedis.del(key);
        if(myMail.getType()==2) jedis.del("application:"+myMail.getSender().toString());
        jedis.close();
        String text;
        if(myMail.getType()==1)//如果为邀请
        {
            text = "【系统消息】邀请未通过，"+ userId +"同学已有队伍或不同意入队";
        }
        else if(myMail.getType()==2)//如果为申请
        {
            text = "【系统消息】队伍申请未通过，队伍ID:"+myMail.getTeamId()+"已满员或不接受申请";
        }
        else return "error678";
        redisService.sendMail(0L,myMail.getSender(),0,0,text);
        return "0";
    }

    /**
     * 删除一个邮件
     * @param mailId 消息编号
     * @return 本地测试通过
     */
    @RequestMapping(value="/delete",method = RequestMethod.DELETE)
    public String deleteMail(@RequestHeader("token")String token,Integer mailId)
    {
        Long userId = redisService.getUserId(token);
        Jedis jedis = jedisPool.getResource();
        jedis.del("mail"+userId+":"+mailId);
        jedis.close();
        return "0";
    }
}
