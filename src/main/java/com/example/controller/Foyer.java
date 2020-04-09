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
}
