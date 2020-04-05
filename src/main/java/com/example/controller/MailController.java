package com.example.controller;
import com.example.domain.Mail;
import com.example.domain.Team;
import com.example.domain.User;
import com.example.service.IMailService;
import com.example.service.ITeamService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("mail")
public class MailController
{
    @Autowired
    private IMailService mailService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ITeamService teamService;

    /**
     * 甲进入消息界面
     * @param userId 甲的学号
     * @return 本地测试通过
     */
    @RequestMapping("")
    public List<Mail> showMails(Long userId)
    {
        userService.updateMessage(userId);
        return mailService.getMailsByReceiver(userId);
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
    public String approve(Integer mailId)
    {
        Mail myMail = mailService.getMail(mailId);
        if(myMail.getType()==1)//如果为邀请
        {
            Long userId = myMail.getReceiver();
            Integer teamId = myMail.getTeamId();
            Team team = teamService.getTeam(teamId);
            if(userService.hasATeam(userId,team.getCourse_id())) return "2";//You have a team
            if(!team.getAvailable()) return "3";//The team you applied is full or not available
            teamService.addAMember(teamId,userId);
            userService.updateTeamId(userId,team.getCourse_id(),teamId);
            mailService.deleteMail(mailId);
            mailService.sendMail(0L,team.getLeader(),0,0,"邀请被通过，成员 "+ userId +" 已入队");//发一个系统邮件，告知队长入队成功
        }
        else if(myMail.getType()==2)//如果为申请
        {
            Long userId = myMail.getSender();
            Integer teamId = myMail.getTeamId();
            Team team = teamService.getTeam(teamId);
            if(!team.getAvailable()) return "5";//Your team is full or not available
            if(team.getCurrent_num().equals(team.getMax_num())) return "7";//Your team is full
            if(userService.hasATeam(userId,team.getCourse_id())) return "6";//He has a team
            teamService.addAMember(teamId,userId);
            userService.updateTeamId(userId,team.getCourse_id(),teamId);
            mailService.deleteMail(mailId);
            mailService.sendMail(0L,userId,0,0,"队伍申请已通过，你已入队");//发送系统邮件告知申请通过
        }
        return "0";
    }

    /**
     * 拒绝邀请/申请
     * @param mailId 消息编号
     * @return 状态字符串
     */
    @RequestMapping("/reject")
    public String reject(Integer mailId)
    {
        Mail myMail = mailService.getMail(mailId);
        mailService.deleteMail(mailId);
        String text;
        if(myMail.getType()==1)//如果为邀请
        {
            text = "邀请未通过， "+ myMail.getReceiver() +" 同学没有同意入队";
        }
        else if(myMail.getType()==2)//如果为申请
        {
            text = "队伍申请未通过，队伍 ID:"+myMail.getTeamId()+" 已满员或不接受申请";
        }
        else return "error678";
        mailService.sendMail(0L,myMail.getSender(),0,0,text);
        return "0";
    }

    /**
     * 删除一个邮件
     * @param mailId 消息编号
     * @return 本地测试通过
     */
    @RequestMapping(value="/delete",method = RequestMethod.DELETE)
    public String deleteMail(Integer mailId)
    {
        mailService.deleteMail(mailId);
        return "0";
    }
}
