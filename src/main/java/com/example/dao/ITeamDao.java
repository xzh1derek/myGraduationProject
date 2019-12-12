package com.example.dao;

import com.example.domain.Team;

import java.util.List;

public interface ITeamDao
{
    List<Team> queryTeamList();

    List<Team> queryTeamsByLeader(Long num);

    List<Team> queryTeamsByCourse(Integer courseId);

    int createTeam(Long leader, Integer courseId, Integer maxNum);

    Team getTeam(Integer teamId);

    void updateTeam(Team team);

    void updateAvailable(int teamId,boolean status);

    void updateDisplay(int teamId, boolean status);

    Boolean existTeam(int teamId);
}
