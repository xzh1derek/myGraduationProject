package com.example.controller;
import com.example.domain.Mail;
import com.example.domain.Team;
import com.example.domain.UserCourse;
import com.example.service.IMailService;
import com.example.service.ITeamService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class Invite
{
    @Autowired
    private IUserService userService;
    @Autowired
    private ITeamService teamService;
    @Autowired
    private IMailService mailService;

    /**
     * 甲邀请乙同学加入队伍
     * @param teamId 队伍编号
     * @param receiver 乙同学学号
     * @return 状态字符串 本地测试通过
     */
    @RequestMapping(value = "invite", method = RequestMethod.POST)
    @ResponseBody
    public String invite(Integer teamId, Long receiver)
    {
        if(!userService.existUser(receiver)) return "1";//User does not exist.
        Team team = teamService.getTeam(teamId);
        if(userService.userMatchCourse(receiver,team.getCourseId())) return "8";//He is not involved in this course.
        if(userService.hasATeam(receiver,team.getCourseId())) return "6";//He has a team.
        else if(team.getCurrentNum()==team.getMaxNum()) return "7";//Your team is full
        else{
            Mail mail = new Mail();
            mail.setSender(team.getLeader());
            mail.setReceiver(receiver);
            mail.setType(1);//send a invitation
            mail.setTeamId(teamId);
            mail.setText("邀请入队");
            mailService.sendMail(mail);
            return "0";
        }
    }

    /**
     * 乙同学同意邀请
     * @param mailId 邮件编号
     * @return 状态字符串 本地测试通过
     */
    @RequestMapping(value = "approveInvite",method = RequestMethod.POST)
    @ResponseBody
    public String approveInvite(Integer mailId)
    {
        Long userId = mailService.getMail(mailId).getReceiver();
        Integer teamId = mailService.getMail(mailId).getTeamId();
        Team team = teamService.getTeam(teamId);
        if(userService.hasATeam(userId,team.getCourseId())) return "2";//You have a team
        if(!team.isAvailable()) return "3";//The team you applied is full or not available
        teamService.addAMember(teamId,userId);
        userService.updateTeamId(userId,teamId,team.getCourseId());
        mailService.deleteMail(mailId);
        Mail mail = new Mail();
        mail.setSender(0L);
        mail.setReceiver(team.getLeader());
        mail.setType(0);
        mail.setText("邀请被通过，成员"+ userId +"已入队");
        mailService.sendMail(mail);//发一个系统邮件，告知队长入队成功
        return "0";
    }

    /**
     * 乙同学拒绝邀请
     * @param mailId 邮件编号
     * @return 状态字符串 本地测试通过
     */
    @RequestMapping(value = "rejectInvite",method = RequestMethod.POST)
    @ResponseBody
    public String rejectInvite(Integer mailId)
    {
        Integer teamId = mailService.getMail(mailId).getTeamId();
        mailService.deleteMail(mailId);
        Mail mail = new Mail();
        mail.setSender(0L);
        mail.setReceiver(teamService.getTeam(teamId).getLeader());
        mail.setType(0);
        mail.setText("邀请未通过，"+ mailService.getMail(mailId).getReceiver() +"没有同意入队");
        mailService.sendMail(mail);
        return "0";
    }
}
