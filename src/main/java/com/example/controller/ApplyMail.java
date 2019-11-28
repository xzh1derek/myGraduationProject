package com.example.controller;
import com.example.domain.Mail;
import com.example.domain.Team;
import com.example.domain.User;
import com.example.service.IMailService;
import com.example.service.ITeamService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class ApplyMail
{
    @Autowired
    private ITeamService teamService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IMailService mailService;

    /**
     * 丙申请加入甲的队伍
     * @param user1 丙的学号
     * @param user2 甲的学号
     * @return 返回状态信息 测试通过
     */
    @RequestMapping(value = "apply",method = RequestMethod.GET)
    @ResponseBody
    public String apply(@RequestParam("sender")String user1,@RequestParam("receiver")String user2)
    {
        Long sender=Long.parseLong(user1),receiver=Long.parseLong(user2);
        if(!userService.existUser(receiver)) return "1";//User does not exist.
        if(userService.getUser(sender).getTeamleader()!=0L) return "2";//You have a team
        if(!teamService.getTeam(receiver).isAvailable()) return "3";//The team you applied is not available.
        if(mailService.existMail(sender)) return "4";//You have an untreated application, you can't apply for another team.
        mailService.newMail(sender,receiver);
        userService.updateApplication(sender,"0");
        return "0";
    }

    /**
     * 丙撤回申请
     * @param id 丙的学号 测试通过
     */
    @RequestMapping(value = "withdraw",method = RequestMethod.GET)
    @ResponseBody
    public String withdraw(@RequestParam("userId")String id)
    {
        Long userId = Long.parseLong(id);
        mailService.deleteMail(userId);
        userService.updateApplication(userId,null);
        return "0";
    }

    /**
     * 甲进入邮箱界面
     * @param id 甲的学号
     * @return 所有申请者的信息 测试通过
     */
    @RequestMapping(value="mail",method = RequestMethod.GET)
    @ResponseBody
    public List<User> showMail(@RequestParam("userId")String id)
    {
        Long userId = Long.parseLong(id);
        List<Mail> mailList = mailService.getMails(userId);
        List<User> userList = new ArrayList<>();
        for(Mail mail : mailList)
        {
            userList.add(userService.getUser(mail.getSender()));
        }
        return userList;
    }

    /**
     * 甲同意丙的申请
     * @param user1 丙的学号
     * @param user2 甲的学号
     * @return 状态信息 测试通过
     */
    @RequestMapping(value="approve",method = RequestMethod.GET)
    @ResponseBody
    public String approveApplication(@RequestParam("sender")String user1,@RequestParam("receiver")String user2)
    {
        Long sender=Long.parseLong(user1),receiver=Long.parseLong(user2);
        Team team = teamService.getTeam(receiver);
        if(!team.isAvailable()) return "5";//Your team is full or not available
        if(team.getCurrent_num()==team.getMax_num()) return "7";//Your team is full
        if(userService.getUser(sender).getTeamleader()!=0L) return "6";//He has a team
        teamService.addAMember(receiver,sender);
        userService.updateTeam(sender,receiver);
        userService.updateApplication(sender,"你的申请已通过");
        userService.updateInvitation(sender,0L);
        mailService.deleteMail(sender);
        if(team.getCurrent_num()==team.getMax_num())//加完人后判断小组是否满员，是则自动删除所有相关申请邮件
        {
            List<Mail> mails = mailService.getMails(receiver);
            for(Mail mail : mails)
            {
                Long id = mail.getSender();
                mailService.deleteMail(id);
                userService.updateApplication(id,"申请未通过：小组已满员");
            }
        }
        return "0";
    }

    /**
     * 甲拒绝丙的申请
     * @param user 丙的学号
     * @return 状态信息 测试通过
     */
    @RequestMapping(value="reject",method = RequestMethod.GET)
    @ResponseBody
    public String rejectApplication(@RequestParam("sender")String user)
    {
        Long sender = Long.parseLong(user);
        mailService.deleteMail(sender);
        userService.updateApplication(sender,"申请未通过，对方拒接了你的申请");
        return "0";
    }
}
