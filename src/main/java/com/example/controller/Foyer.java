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

import java.util.List;

@Controller
@RequestMapping("")
public class Foyer
{
    @Autowired
    private ITeamService teamService;
    @Autowired
    private IUserService userService;

    /**
     * 进入大厅，显示所有（愿意被显示的）组的信息
     * @return 组的列表 多表联查 测试通过
     */
    @RequestMapping(value = "foyer",method = RequestMethod.GET)
    @ResponseBody
    public List<Team> queryTeamList()
    {
        return teamService.queryTeamList();
    }

    /**
     * 搜索某个队长的队伍
     * @param user 队长学号
     * @return 没有就是null，有就是team对象 多表联查 测试通过
     */
    @RequestMapping(value = "search",method = RequestMethod.GET)
    @ResponseBody
    public List<Team> queryOneTeam(@RequestParam("leader")String user)
    {
        Long leader = Long.parseLong(user);
        if(!teamService.existTeam(leader)) return null;
        return teamService.queryATeam(leader);
    }
}
