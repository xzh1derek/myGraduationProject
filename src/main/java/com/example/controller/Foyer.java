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
     * @return 组的列表 多表联查 本地测试通过
     */
    @RequestMapping(value = "foyer",method = RequestMethod.GET)
    @ResponseBody
    public List<Team> queryTeamList()
    {
        return teamService.queryTeamList();
    }

    /**
     * 搜索某个队长的队伍
     * @param leader 队长学号
     * @return 组的列表 多表联查 本地测试通过
     */
    @RequestMapping(value = "searchLeader",method = RequestMethod.GET)
    @ResponseBody
    public List<Team> queryTeamsByLeader(Long leader)
    {
        return teamService.queryTeamsByLeader(leader);
    }

    /**
     * 搜索特定课程的队伍
     * @param courseId 课程编号
     * @return 组的列表 多表联查 本地测试通过
     */
    @RequestMapping(value = "searchCourse",method = RequestMethod.GET)
    @ResponseBody
    public List<Team> queryTeamsByCourse(Integer courseId)
    {
        return teamService.queryTeamsByCourse(courseId);
    }
}
