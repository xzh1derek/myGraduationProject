package com.example.controller;
import com.example.config.redis.RedisService;
import com.example.domain.Team;
import com.example.domain.UserCourse;
import com.example.service.ICourseService;
import com.example.service.ITeamService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
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
    @Autowired
    private RedisService redisService;

    /**
     * 返回自己所有加的队伍
     */
    @RequestMapping(value = "")
    public List<Team> showMyTeam(@RequestHeader("token")String token)
    {
        return teamService.showMyTeam(redisService.getUserId(token));
    }

    /**
     * 所有可组队的课程（需要组队且未组队）
     */
    @RequestMapping(value = "/myCourse")
    public List<UserCourse> myCourse(@RequestHeader("token")String token)
    {
        Long userId = redisService.getUserId(token);
        List<UserCourse> myUserCourses = new ArrayList<>();
        List<UserCourse> userCourses = userService.getUserCourses(userId);
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
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public String createTeam(@RequestHeader("token")String token, Integer courseId)
    {
        Long userId = redisService.getUserId(token);
        if(userService.hasATeam(userId,courseId)) return "2";//You have a team
        Integer teamId = teamService.createTeam(userId,courseId,courseService.getCourse(courseId).getMax_num());
        userService.updateIsLeader(userId,courseId,true);
        userService.updateTeamId(userId,courseId,teamId);
        return "0";
    }

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
        if(!userService.userMatchCourse(receiver,team.getCourse_id())) return "8";//He is not involved in this course.
        if(userService.hasATeam(receiver,team.getCourse_id())) return "6";//He has a team.
        else if(team.getCurrent_num().equals(team.getMax_num())) return "7";//Your team is full
        else{
            redisService.sendMail(team.getLeader(),receiver,1,teamId,"【组队邀请】"+team.getLeader()+" 同学邀请你加入队伍ID: "+teamId);
            return "0";
        }
    }

    /**
     * 在组队管理界面中关闭/打开一支队伍的申请
     * @param teamId 队伍编号
     * @param status 1=接收申请 0=不接受申请
     * @return 状态码 本地测试通过
     */
    @RequestMapping(value = "/setAvailable",method = RequestMethod.POST)
    public String setAvailable(Integer teamId, Boolean status)
    {
        Team team = teamService.getTeam(teamId);
        if(status)
        {
            if(team.getCurrent_num().equals(team.getMax_num())) return "7";//Your team is full
            else teamService.updateAvailable(teamId,true);
        }
        else
        {
            teamService.updateAvailable(teamId,false);
            teamService.updateDisplay(teamId,false);
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
    public String setDisplay(Integer teamId, Boolean status)
    {
        if(status) {
            teamService.updateDisplay(teamId,true);
        }
        else teamService.updateDisplay(teamId,false);
        return "0";
    }
}
