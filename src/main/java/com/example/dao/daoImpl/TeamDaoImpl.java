package com.example.dao.daoImpl;

import com.example.dao.ITeamDao;
import com.example.domain.Team;
import com.example.mapper.TeamMapper;
import com.example.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamDaoImpl implements ITeamDao
{
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public List<Team> queryTeamList(Integer rows, Integer page)
    {
        return teamMapper.queryTeamList(rows,rows*(page-1));
    }

    @Override
    public List<Team> queryTeamsByLeader(Long num)
    {
        return teamMapper.queryTeamsByLeader(num);
    }

    @Override
    public List<Team> queryTeamsByCourse(Integer courseId,Integer rows, Integer page)
    {
        return teamMapper.queryTeamsByCourse(courseId,rows,rows*(page-1));
    }

    @Override
    public int createTeam(Long num,Integer courseId,Integer maxNum)
    {
        Team team = new Team();
        team.setLeader(num);
        team.setCourse_id(courseId);
        team.setMax_num(maxNum);
        teamMapper.createTeam(team);
        return team.getId();
    }

    @Override
    public void deleteTeam(Integer teamId)
    {
        teamMapper.deleteTeam(teamId);
    }

    @Override
    public Team getTeam(Integer teamId)
    {
        return teamRepository.getOne(teamId);
    }

    @Override
    public Team queryTeamById(Integer teamId)
    {
        return teamMapper.queryTeamById(teamId);
    }

    @Override
    public void updateTeam(Team team)
    {
        teamRepository.save(team);
    }

    @Override
    public void updateAvailable(int teamId, boolean status)
    {
        teamMapper.updateAvailable(teamId,status);
    }

    @Override
    public void updateDisplay(int teamId, boolean status)
    {
        teamMapper.updateDisplay(teamId,status);
    }

    @Override
    public Integer queryTeamNumbers()
    {
        return teamMapper.queryTeamNumbers();
    }

    @Override
    public Integer queryTeamNumbersByCourse(Integer courseId)
    {
        return teamMapper.queryTeamNumbersByCourse(courseId);
    }
}
