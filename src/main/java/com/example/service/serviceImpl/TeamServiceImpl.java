package com.example.service.serviceImpl;

import com.example.dao.ITeamDao;
import com.example.dao.IUserDao;
import com.example.domain.Team;
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

    @Override
    public List<Team> queryTeamList()
    {
        return teamDao.queryTeamList();
    }

    @Override
    public List<Team> queryTeamsByLeader(Long num)
    {
        return teamDao.queryTeamsByLeader(num);
    }

    @Override
    public List<Team> queryTeamsByCourse(Integer courseId)
    {
        return teamDao.queryTeamsByCourse(courseId);
    }

    @Override
    public Integer createTeam(Long leader,Integer courseId)
    {
        return teamDao.createTeam(leader,courseId);
    }

    @Override
    public Team getTeam(int teamId)
    {
        return teamDao.getTeam(teamId);
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
        if(!team.isAvailable()) return;
        int currentNum = team.getCurrentNum(),maxNum=team.getMaxNum();
        if(currentNum==maxNum) return;
        else if(currentNum==1&&maxNum>1){
            team.setMember1(id);
            team.setCurrentNum(2);
        }else if(currentNum==2&&maxNum>2){
            team.setMember2(id);
            team.setCurrentNum(3);
        }else if(currentNum==3&&maxNum>3){
            team.setMember3(id);
            team.setCurrentNum(4);
        }else if(currentNum==4&&maxNum>4){
            team.setMember4(id);
            team.setCurrentNum(5);
        }else if(currentNum==5&&maxNum>5){
            team.setMember5(id);
            team.setCurrentNum(6);
        }else if(currentNum==6&&maxNum>6){
            team.setMember6(id);
            team.setCurrentNum(7);
        }
        if(team.getCurrentNum()==team.getMaxNum()) team.setAvailable(false);
        teamDao.updateTeam(team);
    }

    @Override
    public List<Team> showMyTeam(Long username)
    {
        List<Team> teams = new ArrayList<>();
        List<UserCourse> userCourses = userDao.getUser(username).getUserCourses();
        for(UserCourse userCourse : userCourses)
        {
            if(userCourse.getTeam_id()==0) continue;
            teams.add(teamDao.getTeam(userCourse.getTeam_id()));
        }
        return teams;
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
    public Boolean existTeam(int teamId)
    {
        return teamDao.existTeam(teamId);
    }
}
