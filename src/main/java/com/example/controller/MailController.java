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
import org.springframework.web.bind.annotation.ResponseBody;
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
     * 同意申请/邀请
     * @param mailId 邮件编号
     * @return 状态字符串
     */
    @RequestMapping("/approve")
    @ResponseBody
    public String approve(Integer mailId)
    {
        Mail myMail = mailService.getMail(mailId);
        if(myMail.getType()==1)//如果为邀请
        {
            Long userId = myMail.getReceiver();
            Integer teamId = myMail.getTeamId();
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
        }
        else if(myMail.getType()==2)//如果为申请
        {
            Long userId = myMail.getSender();
            Integer teamId = myMail.getTeamId();
            Team team = teamService.getTeam(teamId);
            if(!team.isAvailable()) return "5";//Your team is full or not available
            if(team.getCurrentNum()==team.getMaxNum()) return "7";//Your team is full
            if(userService.hasATeam(userId,team.getCourseId())) return "6";//He has a team
            teamService.addAMember(teamId,userId);
            userService.updateTeamId(userId,teamId,team.getCourseId());
            mailService.deleteMail(mailId);
            Mail mail = new Mail();
            mail.setSender(0L);
            mail.setReceiver(userId);
            mail.setType(0);
            mail.setText("队伍申请已通过，你已入队");
            mailService.sendMail(mail);//发送系统邮件告知申请通过
        }
        else return "678";//错误
        return "0";
    }

    /**
     * 拒绝邀请/申请
     * @param mailId 邮件编号
     * @return 状态字符串
     */
    @RequestMapping("/reject")
    @ResponseBody
    public String reject(Integer mailId)
    {
        Mail myMail = mailService.getMail(mailId);
        mailService.deleteMail(mailId);
        Mail mail = new Mail();
        mail.setSender(0L);
        mail.setReceiver(myMail.getSender());
        mail.setType(0);
        if(myMail.getType()==1)//如果为邀请
        {
            mail.setText("邀请未通过，"+ myMail.getReceiver() +"同学没有同意入队");
        }
        else if(myMail.getType()==2)//如果为申请
        {
            mail.setText("队伍申请未通过，队伍ID:"+myMail.getTeamId()+"已满员或不接受申请");
        }
        else return "678";
        mailService.sendMail(mail);
        return "0";
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
