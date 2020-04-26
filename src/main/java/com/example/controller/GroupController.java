package com.example.controller;
import com.example.config.redis.RedisService;
import com.example.domain.Course;
import com.example.domain.Team;
import com.example.domain.UserCourse;
import com.example.service.ICourseService;
import com.example.service.ITeamService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("group")
public class GroupController
{
    @Autowired
    private ITeamService teamService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICourseService courseService;

    /**
     * 显示所有小组的信息 分页查询
     * @return 组的列表
     */
    @RequestMapping("")
    public List<Team> queryTeamList(Integer rows, Integer page)
    {
        return teamService.queryAllTeamList(rows,page);
    }

    /**
     * 获得记录总数
     */
    @RequestMapping("/pages")
    public Integer queryTeamPages()
    {
        return teamService.queryAllTeamNumbers();
    }

    /**
     * 搜索某个队长的队伍
     * @param leader 队长学号
     * @return 组的列表 多表联查 本地测试通过
     */
    @RequestMapping("/search/leader")
    public List<Team> queryTeamsByLeader(Long leader)
    {
        return teamService.queryAllTeamsByLeader(leader);
    }

    /**
     * 搜索特定课程的队伍 分页查询
     * @param courseId 课程编号
     * @return 组的列表 多表联查 本地测试通过
     */
    @RequestMapping("/search/course")
    public List<Team> queryTeamsByCoursePaging(Integer courseId,Integer rows, Integer page)
    {
        return teamService.queryAllTeamsByCourse(courseId,rows,page);
    }

    /**
     * 获得记录总数
     * @param courseId 课程id
     */
    @RequestMapping("/search/course/pages")
    public Integer queryTeamsByCoursePages(Integer courseId)
    {
        return teamService.queryAllTeamNumbersByCourse(courseId);
    }

    /**
     * 删除队伍 并把组员队伍状态置零
     * @param teamId 队伍id
     */
    @RequestMapping(value = "/dismiss",method = RequestMethod.DELETE)
    public String helpStudentOutOfTeam(Integer teamId)
    {
        List<Long> userId = teamService.queryUsernameByTeamId(teamId);
        for(Long username : userId)
        {
            redisService.sendMail(0L,username,0,0,"管理员删除了队伍id："+teamId+"，请重新组队");
        }
        teamService.deleteTeamMembers(teamId);
        teamService.deleteTeam(teamId);
        return "0";
    }

    /**
     * 查询某个课程所有没有组队的同学
     * @param courseId 课程id
     */
    @RequestMapping("/search/course/teamless")
    public List<UserCourse> queryStudentsNotInTeams(Integer courseId)
    {
        return courseService.queryStudentsTeamless(courseId);
    }

    /**
     * 给没组队的学生组队 每位同学单独成队 支持回滚
     * @param courseId 课程id
     * @param userId 学号的数组
     */
    @RequestMapping(value = "/students/single",method = RequestMethod.POST)
    public String groupStudentsSingleHandedly(Integer courseId, @RequestBody Long[] userId)
    {
        Course course = courseService.getCourse(courseId);
        for(Long username : userId)
        {
            if(userService.hasATeam(username,courseId)){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return "错误，学号"+username+"已经组队";
            }
            Integer teamId = teamService.createTeam(username,courseId,course.getMax_num());
            userService.updateIsLeader(username,courseId,true);
            userService.updateTeamId(username,courseId,teamId);
            redisService.sendMail(0L,username,0,0,"系统自动组队通知，你已进入队伍id："+teamId);
        }
        return "0";
    }

    /**
     * 给没组队的学生组队 使这些学生进入一个队伍里 支持回滚
     * @param courseId 课程id
     * @param userId 学号的数组
     */
    @RequestMapping(value = "/students/together",method = RequestMethod.POST)
    @Transactional
    public String groupStudentsAltogether(Integer courseId, @RequestBody Long[] userId)
    {
        Course course = courseService.getCourse(courseId);
        if(userId.length>course.getMax_num()) return "f";
        int i=1,teamId=0;
        for(Long username : userId)
        {
            if(userService.hasATeam(username,courseId)){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return "错误，学号"+username+"已经组队";
            }
            if(i==1){
                teamId = teamService.createTeam(username,courseId,course.getMax_num());
                userService.updateIsLeader(username,courseId,true);
                userService.updateTeamId(username,courseId,teamId);
                redisService.sendMail(0L,username,0,0,"系统自动组队通知，你已进入队伍id："+teamId);
            }else if(i>1&&i<=course.getMax_num()){
                teamService.addAMember(teamId,username);
                userService.updateTeamId(username,courseId,teamId);
                redisService.sendMail(0L,username,0,0,"系统自动组队通知，你已进入队伍id："+teamId);
            }else{
                i=0;
            }
            i++;
        }
        return "0";
    }
}
