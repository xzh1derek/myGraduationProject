package com.example.service.serviceImpl;

import com.example.dao.ICourseDao;
import com.example.dao.ITeamDao;
import com.example.dao.IUserDao;
import com.example.domain.Team;
import com.example.domain.User;
import com.example.domain.UserCourse;
import com.example.service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements ITeamService
{
    @Autowired
    private ITeamDao teamDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private ICourseDao courseDao;

    @Override
    public List<Team> queryTeamList(Integer rows, Integer page)
    {
        return teamDao.queryTeamList(rows, page);
    }

    @Override
    public List<Team> queryTeamsByLeader(Long num)
    {
        return teamDao.queryTeamsByLeader(num);
    }

    @Override
    public Integer queryTeamNumbers()
    {
        return teamDao.queryTeamNumbers();
    }

    @Override
    public Integer queryTeamNumbersByCourse(Integer courseId)
    {
        return teamDao.queryTeamNumbersByCourse(courseId);
    }

    @Override
    public List<Team> queryTeamsByCourse(Integer courseId,Integer rows, Integer page)
    {
        return teamDao.queryTeamsByCourse(courseId,rows,page);
    }

    @Override
    public Integer createTeam(Long leader,Integer courseId,Integer maxNum)
    {
        return teamDao.createTeam(leader,courseId,maxNum);
    }

    @Override
    public Team getTeam(int teamId)
    {
        return teamDao.getTeam(teamId);
    }

    @Override
    public Team queryTeamById(Integer teamId)
    {
        return teamDao.queryTeamById(teamId);
    }

    /**
     * 队伍加人时
     * @param teamId:队伍编号
     * @param id:新组员学号
     */
    @Override
    public void addAMember(int teamId, Long id)
    {
        Team team = teamDao.getTeam(teamId);
        if(!team.getAvailable()) return;
        int currentNum = team.getCurrent_num(),maxNum=team.getMax_num();
        if(currentNum==maxNum) return;
        else if(currentNum==1&&maxNum>1){
            team.setMember1(id);
            team.setCurrent_num(2);
        }else if(currentNum==2&&maxNum>2){
            team.setMember2(id);
            team.setCurrent_num(3);
        }else if(currentNum==3&&maxNum>3){
            team.setMember3(id);
            team.setCurrent_num(4);
        }else if(currentNum==4&&maxNum>4){
            team.setMember4(id);
            team.setCurrent_num(5);
        }else if(currentNum==5&&maxNum>5){
            team.setMember5(id);
            team.setCurrent_num(6);
        }else if(currentNum==6&&maxNum>6){
            team.setMember6(id);
            team.setCurrent_num(7);
        }
        if(team.getCurrent_num()==team.getMax_num()) team.setAvailable(false);
        teamDao.updateTeam(team);
    }

    /**
     * 返回自己的队伍所有成员信息，手动注入
     * @param userId 学号
     * @return 队伍的List
     */
    @Override
    public List<Team> showMyTeam(Long userId)
    {
        List<Team> teams = new ArrayList<>();
        List<UserCourse> userCourses = userDao.getUser(userId).getUserCourses();
        for(UserCourse userCourse : userCourses)
        {
            if(userCourse.getTeam_id()==0) continue;
            Integer teamId = userCourse.getTeam_id();
            Team team = teamDao.getTeam(teamId);
            List<User> memberDetails = new ArrayList<>();
            List<Long> usernames = userDao.queryUsernameByTeamId(teamId);
            for(Long username : usernames)
            {
                memberDetails.add(userDao.findAUser(username));
            }
            team.setMemberDetails(memberDetails);
            team.setCourse(courseDao.getCourse(team.getCourse_id()));
            teams.add(team);
        }
        return teams;
    }

    @Override
    public List<Long> queryUsernameByTeamId(Integer teamId)
    {
        return userDao.queryUsernameByTeamId(teamId);
    }

    @Override
    public void updateAvailable(int teamId, Boolean status)
    {
        teamDao.updateAvailable(teamId,status);
    }

    @Override
    public void updateDisplay(int teamId, boolean status)
    {
        teamDao.updateDisplay(teamId, status);
    }

    @Override
    public void deleteTeam(Integer teamId)
    {
        teamDao.deleteTeam(teamId);
    }

    @Override
    public void deleteTeamMembers(Integer teamId)
    {
        userDao.deleteTeamMembers(teamId);
    }
}
