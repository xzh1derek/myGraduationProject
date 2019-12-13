package com.example.controller;
import com.example.domain.Team;
import com.example.domain.UserCourse;
import com.example.service.ICourseService;
import com.example.service.ITeamService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("team")
public class TeamController
{
    @Autowired
    private IUserService userService;
    @Autowired
    private ITeamService teamService;
    @Autowired
    private ICourseService courseService;

    /**
     * 返回自己所有加的队伍
     * @param userId 自己的学号
     * @return 队伍的List 多表联查 本地测试通过
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<Team> showMyTeam(Long userId)
    {
        return teamService.showMyTeam(userId);
    }

    /**
     * 所有可组队的课程（需要组队且未组队）
     * @param userId 学号
     * @return 待选课程的List
     */
    @RequestMapping(value = "/myCourse",method = RequestMethod.GET)
    public List<UserCourse> myCourse(Long userId)
    {
        List<UserCourse> myUserCourses = new ArrayList<>();
        List<UserCourse> userCourses = userService.getUserCourses(userId);
        System.out.println(userCourses);
        for(UserCourse userCourse : userCourses)
        {
            if(userCourse.getTeam_id()==0&&userCourse.getCourse().getIs_team())
            {
                myUserCourses.add(userCourse);
            }
        }
        return myUserCourses;
    }

    /**
     * 点击创建队伍并绑定课程
     * @param userId 学号
     * @param courseId 绑定的课程序号
     * @return 状态码 本地测试通过
     */
    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public String createTeam(Long userId, Integer courseId)
    {
        if(userService.hasATeam(userId,courseId)) return "2";//You have a team
        Integer teamId = teamService.createTeam(userId,courseId,courseService.getCourse(courseId).getMax_num());
        UserCourse userCourse = userService.getUserCourse(userId,courseId);
        userService.updateLeader(userCourse);
        userService.updateTeamId(userCourse,teamId);
        return "0";
    }

    /**
     * 在组队管理界面中关闭/打开一支队伍的申请
     * @param teamId 队伍编号
     * @param status 1=接收申请 0=不接受申请
     * @return 状态码 本地测试通过
     */
    @RequestMapping(value = "/setAvailable",method = RequestMethod.POST)
    public String setAvailable(Integer teamId, String status)
    {
        Team team = teamService.getTeam(teamId);
        if(status.equals("1"))
        {
            if(team.getCurrentNum()==team.getMaxNum()) return "7";//Your team is full
            else teamService.updateAvailable(teamId,true);
        }
        else if(status.equals("0"))
        {
            teamService.updateAvailable(teamId,false);
        }
        return "0";
    }

    /**
     * 甲同学在组队管理界面中打开/关闭发布组队
     * @param teamId 队伍编号
     * @param status 1=打开发布组队 0=关闭发布
     * @return 状态码 本地测试通过
     */
    @RequestMapping(value = "/setDisplay", method = RequestMethod.POST)
    public String setDisplay(Integer teamId, String status)
    {
        if(status.equals("1")) teamService.updateDisplay(teamId,true);
        else teamService.updateDisplay(teamId,false);
        return "0";
    }
}
