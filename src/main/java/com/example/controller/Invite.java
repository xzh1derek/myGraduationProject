package com.example.controller;
import com.example.domain.Mail;
import com.example.domain.Team;
import com.example.service.IMailService;
import com.example.service.ITeamService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Invite
{
    @Autowired
    private IUserService userService;
    @Autowired
    private ITeamService teamService;
    @Autowired
    private IMailService mailService;

    /**
     * 队长邀请乙同学加入队伍
     * @param teamId 队伍编号
     * @param receiver 乙同学学号
     * @return 状态字符串 本地测试通过
     */
    @RequestMapping(value = "invite", method = RequestMethod.POST)
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
            mail.setText(team.getLeader()+" 同学邀请你加入队伍ID: "+teamId);
            mailService.sendMail(mail);
            return "0";
        }
    }
}
