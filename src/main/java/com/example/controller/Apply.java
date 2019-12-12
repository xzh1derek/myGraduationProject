package com.example.controller;
import com.example.domain.Mail;
import com.example.domain.Team;
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
@RequestMapping("")
public class Apply
{
    @Autowired
    private ITeamService teamService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IMailService mailService;

    /**
     * 丙申请加入某队伍
     * @param sender 丙的学号
     * @param teamId 队伍编号
     * @return 返回状态码 本地测试通过
     */
    @RequestMapping(value = "apply",method = RequestMethod.POST)
    @ResponseBody
    public String apply(Long sender, Integer teamId)
    {
        Team team = teamService.getTeam(teamId);
        if(userService.userMatchCourse(sender,team.getCourseId())) return "9";//Your course does not match the team's curriculum
        if(userService.hasATeam(sender,team.getCourseId())) return "2";//You have a team
        if(!team.isAvailable()) return "3";//The team you applied is full or not available.
        if(mailService.hasApplied(sender)) return "4";//You have an untreated application, you can't apply for another team.
        Mail mail = new Mail();
        mail.setSender(sender);
        mail.setReceiver(team.getLeader());
        mail.setType(2);//Send an application
        mail.setTeamId(teamId);
        mail.setText(sender+"同学申请加入队伍ID:"+teamId);
        mailService.sendMail(mail);
        return "0";
    }

    /**
     * 我的申请
     * @param userId 学号
     * @return 申请邮件 本地测试通过
     */
    @RequestMapping("myApplication")
    @ResponseBody
    public List<Mail> myApplication(Long userId)
    {
        return mailService.myApplication(userId);
    }

    /**
     * 丙撤回申请
     * @param mailId 邮件编号
     * @return 状态码 本地测试通过
     */
    @RequestMapping(value = "withdraw",method = RequestMethod.POST)
    @ResponseBody
    public String withdraw(Integer mailId)
    {
        mailService.deleteMail(mailId);
        return "0";
    }
}
