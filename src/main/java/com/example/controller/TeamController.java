package com.example.controller;
import com.example.domain.Team;
import com.example.domain.UserCourse;
import com.example.service.ICourseService;
import com.example.service.IMailService;
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
    @Autowired
    private IMailService mailService;

    /**
     * 返回自己所有加的队伍
     * @param userId 自己的学号
     * @return 队伍的List 多表联查 本地测试通过
     */
    @RequestMapping(value = "")
    public List<Team> showMyTeam(Long userId)
    {
        return teamService.showMyTeam(userId);
    }

    /**
     * 所有可组队的课程（需要组队且未组队）
     * @param userId 学号
     * @return 待选课程的List
     */
    @RequestMapping(value = "/myCourse")
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
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public String createTeam(Long userId, Integer courseId)
    {
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
            mailService.sendMail(team.getLeader(),receiver,1,teamId,team.getLeader()+" 同学邀请你加入队伍ID: "+teamId);
            return "0";
        }
    }

    /**
     * 在组队管理界面提交发布申请和对外展示
     * @param teamId 队伍编号
     * @return 状态码 本地测试通过
     */
    @RequestMapping(value = "/setStatus",method = RequestMethod.POST)
    public String setStatus(Integer teamId, Boolean available, Boolean display)
    {
        Team team = teamService.getTeam(teamId);
        if(available)
        {
            if(team.getCurrent_num().equals(team.getMax_num())) return "7";//Your team is full
            else teamService.updateAvailable(teamId,true);
        }
        else
        {
            teamService.updateAvailable(teamId,false);
            teamService.updateDisplay(teamId,false);
        }
        if(display) teamService.updateDisplay(teamId,true);
        else teamService.updateDisplay(teamId,false);
        return "0";
    }
}
