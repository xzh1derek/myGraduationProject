package com.example.dao;

import com.example.domain.Team;

import java.util.List;

public interface ITeamDao
{
    List<Team> queryTeamList(Integer rows, Integer page);

    List<Team> queryTeamsByLeader(Long num);

    List<Team> queryTeamsByCourse(Integer courseId, Integer rows, Integer page);

    int createTeam(Long leader, Integer courseId, Integer maxNum);

    Team getTeam(Integer teamId);

    void updateTeam(Team team);

    void updateAvailable(int teamId,boolean status);

    void updateDisplay(int teamId, boolean status);

    Integer queryTeamNumbers();

    void deleteTeam(Integer teamId);

    Integer queryTeamNumbersByCourse(Integer courseId);
}
