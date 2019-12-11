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
     * 甲进入邮箱界面
     * @param userId 甲的学号
     * @return 本地测试通过
     */
    @RequestMapping("")
    @ResponseBody
    public List<Mail> showMails(Long userId)
    {
        return mailService.getMailsByReceiver(userId);
    }

    /**
     * 申请者详细信息
     * @param sender 学号
     * @return 本地测试通过
     */
    @RequestMapping("/userDetail")
    @ResponseBody
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
    @ResponseBody
    public Team showTeamDetail(Integer teamId)
    {
        return teamService.getTeam(teamId);
    }

    /**
     * 删除一个邮件
     * @param mailId 邮件编号
     * @return 本地测试通过
     */
    @RequestMapping(value="/delete",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteMail(Integer mailId)
    {
        mailService.deleteMail(mailId);
        return "0";
    }
}
