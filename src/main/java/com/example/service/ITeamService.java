package com.example.service;

import com.example.domain.Team;

import java.util.List;

public interface ITeamService
{
    List<Team> queryTeamList();

    List<Team> queryATeam(Long team);

    void createTeam(Long leader);

    Team getTeam(Long leader);

    void addAMember(Long leader, Long id);

    void updateAvailable(Long leader, Boolean status);

    void updateDisplay(Long leader, boolean status);

    Boolean existTeam(Long leader);
}
