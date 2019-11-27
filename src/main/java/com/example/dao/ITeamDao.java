package com.example.dao;

import com.example.domain.Team;

import java.util.List;

public interface ITeamDao
{
    List<Team> queryTeamList();

    List<Team> queryATeam(Long num);

    void createTeam(Long leader);

    Team getTeam(Long leader);

    void updateTeam(Team team);

    void updateAvailable(Long leader,boolean status);

    void updateDisplay(Long leader, boolean status);

    Boolean existTeam(Long leader);
}
