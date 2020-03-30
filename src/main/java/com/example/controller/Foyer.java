package com.example.controller;

import com.example.domain.Course;
import com.example.domain.Team;
import com.example.domain.UserCourse;
import com.example.service.ICourseService;
import com.example.service.IMailService;
import com.example.service.ITeamService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("foyer")
public class Foyer
{
    @Autowired
    private ITeamService teamService;
    @Autowired
    private IMailService mailService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICourseService courseService;

    /**
     * 进入大厅，显示所有（愿意被显示的）组的信息 分页查询
     * @return 组的列表 多表联查 本地测试通过
     */
    @RequestMapping("")
    public List<Team> queryTeamList(Integer rows, Integer page)
    {
        return teamService.queryTeamList(rows,page);
    }

    /**
     * 获得记录总数
     */
    @RequestMapping("/pages")
    public Integer queryTeamPages()
    {
        return teamService.queryTeamNumbers();
    }

    /**
     * 搜索某个队长的队伍
     * @param leader 队长学号
     * @return 组的列表 多表联查 本地测试通过
     */
    @RequestMapping("/search/leader")
    public List<Team> queryTeamsByLeader(Long leader)
    {
        return teamService.queryTeamsByLeader(leader);
    }

    /**
     * 搜索特定课程的队伍 分页查询
     * @param courseId 课程编号
     * @return 组的列表 多表联查 本地测试通过
     */
    @RequestMapping("/search/course")
    public List<Team> queryTeamsByCoursePaging(Integer courseId,Integer rows, Integer page)
    {
        return teamService.queryTeamsByCourse(courseId,rows,page);
    }

    /**
     * 获得记录总数
     * @param courseId 课程id
     */
    @RequestMapping("/search/course/pages")
    public Integer queryTeamsByCoursePages(Integer courseId)
    {
        return teamService.queryTeamNumbersByCourse(courseId);
    }

    /**
     * 删除队伍 并把组员队伍状态置零
     * @param teamId 队伍id
     */
    @RequestMapping(value = "/team/delete",method = RequestMethod.DELETE)
    public String helpStudentOutOfTeam(Integer teamId)
    {
        List<Long> userId = teamService.queryUsernameByTeamId(teamId);
        for(Long username : userId)
        {
            mailService.sendMail(0L,username,0,null,"管理员删除了队伍id："+teamId+"，请重新组队");
        }
        teamService.deleteTeamMembers(teamId);
        teamService.deleteTeam(teamId);
        return "0";
    }

    /**
     * 查询某个课程所有没有组队的同学
     * @param courseId 课程id
     */
    @RequestMapping("/students/teamless")
    public List<UserCourse> queryStudentsNotInTeams(Integer courseId)
    {
        return courseService.queryStudentsTeamless(courseId);
    }

    /**
     * 管理员批量给学生发布公告
     * @param text 公告文
     * @param userId 学号的数组
     */
    @RequestMapping(value = "/students/announce",method = RequestMethod.POST)
    public String sendAnnouncement(String text,@RequestBody Long[] userId)
    {
        for(Long username : userId)
        {
            mailService.sendMail(0L,username,0,null,text);
        }
        return "0";
    }

    /**
     * 给没组队的学生组队 每位同学单独成队 支持回滚
     * @param courseId 课程id
     * @param userId 学号的数组
     */
    @RequestMapping(value = "/students/team/alone",method = RequestMethod.POST)
    public String groupStudentsSingleHandedly(Integer courseId, Long[] userId)
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
            mailService.sendMail(0L,username,0,null,"系统自动组队通知，你已进入队伍id："+teamId);
        }
        return "0";
    }

    /**
     * 给没组队的学生组队 按课程最大组员数 顺序配对 支持回滚
     * @param courseId 课程id
     * @param userId 学号的数组
     */
    @RequestMapping(value = "/students/team/together",method = RequestMethod.POST)
    @Transactional
    public String groupStudentsAltogether(Integer courseId, Long[] userId)
    {
        Arrays.sort(userId);
        Course course = courseService.getCourse(courseId);
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
                mailService.sendMail(0L,username,0,null,"系统自动组队通知，你已进入队伍id："+teamId);
            }else if(i>1&&i<=course.getMax_num()){
                teamService.addAMember(teamId,username);
                userService.updateTeamId(username,courseId,teamId);
                mailService.sendMail(0L,username,0,null,"系统自动组队通知，你已进入队伍id："+teamId);
            }else{
                i=0;
            }
            i++;
        }
        return "0";
    }
}
