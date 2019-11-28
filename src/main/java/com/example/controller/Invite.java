package com.example.controller;

import com.example.domain.Team;
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

    /**
     * 甲同学邀请乙同学加入队伍
     * @param user1 甲同学学号
     * @param user2 乙同学学号
     * @return 状态字符串 测试通过
     */
    @RequestMapping(value = "invite", method = RequestMethod.POST)
    @ResponseBody
    public String invite(@RequestParam("sender") String user1, @RequestParam("receiver") String user2)
    {
        Long sender = Long.parseLong(user1);
        Long receiver = Long.parseLong(user2);
        if(!userService.existUser(receiver)) return "1";//User does not exist.
        Long leader = userService.getUser(receiver).getTeamleader();
        Team myteam = teamService.getTeam(sender);
        if(leader!=0L) return "6";//He has a team.
        else if(myteam.getCurrent_num()==myteam.getMax_num()) return "7";//Your team is full
        else{
            userService.updateInvitation(receiver,sender);
            return "0";
        }
    }

    /**
     * 乙同学回应甲同学的邀请信息
     * @param num 乙同学学号
     * @param answer 回复信息（是/否)
     * 测试通过
     */
    @RequestMapping(value = "answerInvite",method = RequestMethod.POST)
    @ResponseBody
    public String answerInvite(@RequestParam("userId")String num, @RequestParam("answer")String answer)
    {
        Long userId = Long.parseLong(num);
        Long teamleader = userService.getUser(userId).getInvitation_id();
        if(answer.equals("1"))
        {
            if(userService.getUser(userId).getTeamleader()!=0L) {
                userService.updateInvitation(userId,0L);
                return "2";//You have a team
            }
            Team team = teamService.getTeam(teamleader);
            if(team.getMax_num()==team.getCurrent_num()){
                userService.updateInvitation(userId,0L);
                return "3";//The team you applied is full or not available
            }
            teamService.addAMember(teamleader,userId);
            userService.updateTeam(userId,teamleader);
            userService.updateApplication(userId,null);
        }
        userService.updateInvitation(userId,0L);
        return "0";
    }
}
