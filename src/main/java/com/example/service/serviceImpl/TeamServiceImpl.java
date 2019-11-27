package com.example.service.serviceImpl;

import com.example.dao.ITeamDao;
import com.example.domain.Team;
import com.example.service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements ITeamService
{
    @Autowired
    private ITeamDao teamDao;

    @Override
    public List<Team> queryTeamList()
    {
        return teamDao.queryTeamList();
    }

    @Override
    public List<Team> queryATeam(Long num)
    {
        return teamDao.queryATeam(num);
    }

    @Override
    public void createTeam(Long leader)
    {
        teamDao.createTeam(leader);
    }

    @Override
    public Team getTeam(Long leader)
    {
        return teamDao.getTeam(leader);
    }

    /**
     * 小组加人时
     * @param leader:组长学号
     * @param id:新组员学号
     */
    @Override
    public void addAMember(Long leader, Long id)
    {
        Team team = teamDao.getTeam(leader);
        if(!team.isAvailable()) return;
        int currentNum = team.getCurrent_num(),maxNum=team.getMax_num();
        if(currentNum==1&&maxNum>1){
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
        }
        if(team.getCurrent_num()==team.getMax_num()) team.setAvailable(false);
        teamDao.updateTeam(team);
    }

    @Override
    public void updateAvailable(Long leader, Boolean status)
    {
        teamDao.updateAvailable(leader,status);
    }

    @Override
    public void updateDisplay(Long leader, boolean status)
    {
        teamDao.updateDisplay(leader, status);
    }

    @Override
    public Boolean existTeam(Long leader)
    {
        return teamDao.existTeam(leader);
    }
}
