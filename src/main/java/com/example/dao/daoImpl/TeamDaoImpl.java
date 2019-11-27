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
    public List<Team> queryTeamList()
    {
        return teamMapper.queryTeamList();
    }

    @Override
    public List<Team> queryATeam(Long num)
    {
        return teamMapper.queryATeam(num);
    }

    @Override
    public void createTeam(Long num)
    {
        teamMapper.createTeam(num);
    }

    @Override
    public Team getTeam(Long num)
    {
        return teamRepository.getOne(num);
    }

    @Override
    public void updateTeam(Team team)
    {
        teamRepository.save(team);
    }

    @Override
    public void updateAvailable(Long leader, boolean status)
    {
        teamMapper.updateAvailable(leader,status);
    }

    @Override
    public void updateDisplay(Long leader, boolean status)
    {
        teamMapper.updateDisplay(leader,status);
    }

    @Override
    public Boolean existTeam(Long leader)
    {
        return teamRepository.existsById(leader);
    }
}
