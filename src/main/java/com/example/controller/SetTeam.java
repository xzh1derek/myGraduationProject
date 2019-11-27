package com.example.controller;

import com.example.domain.Team;
import com.example.domain.User;
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
@RequestMapping("team")
public class SetTeam
{
    @Autowired
    private IUserService userService;
    @Autowired
    private ITeamService teamService;

    /**
     * 返回自己的队伍
     * @param user 队长学号
     * @return 有就是team下的所有user类的List，没就是null 测试通过
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public List<User> showMyTeam(@RequestParam("leader")String user)
    {
        long leader = Long.parseLong(user);
        if(leader==0L) return null;
        return userService.findByTeamleader(leader);
    }

    /**
     * 甲同学点击创建队伍
     * @param user 甲同学学号 测试通过
     */
    @RequestMapping("/create")
    @ResponseBody
    public String createTeam(@RequestParam("leader") String user)
    {
        Long username = Long.parseLong(user);
        if(userService.getUser(username).getTeamleader()!=0L) return "2";//You have a team
        teamService.createTeam(username);
        userService.updateLeader(username,true);
        userService.updateTeam(username,username);
        userService.updateApplication(username,null);
        return "0";
    }

    /**
     * 甲同学在组队管理界面中关闭/打开申请
     * @param user 甲同学学号
     * @param status 1=接收申请 0=不接受申请
     * @return 测试通过
     */
    @RequestMapping(value = "/setAvailable",method = RequestMethod.GET)
    @ResponseBody
    public String setAvailable(@RequestParam("leader")String user, @RequestParam("status")String status)
    {
        Long userId = Long.parseLong(user);
        Team team = teamService.getTeam(userId);
        if(status.equals("1"))
        {
            if(team.getCurrent_num()==team.getMax_num()) return "7";//Your team is full
            else teamService.updateAvailable(userId,true);
        }
        else if(status.equals("0"))
        {
            teamService.updateAvailable(userId,false);
        }
        return "0";
    }

    /**
     * 甲同学在组队管理界面中打开/关闭发布组队
     * @param user 甲同学学号
     * @param status 1=打开发布组队 0=关闭发布
     * @return 测试通过
     */
    @RequestMapping(value = "/setDisplay", method = RequestMethod.GET)
    @ResponseBody
    public String setDisplay(@RequestParam("leader")String user, @RequestParam("status")String status)
    {
        Long userId = Long.parseLong(user);
        if(status.equals("1")) teamService.updateDisplay(userId,true);
        else teamService.updateDisplay(userId,false);
        return "0";
    }
}
