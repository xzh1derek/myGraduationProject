package com.example.service;

import com.example.domain.Team;

import java.util.List;

public interface ITeamService
{
    List<Team> queryTeamList(Integer rows, Integer page);

    List<Team> queryTeamsByLeader(Long leader);

    List<Team> queryTeamsByCourse(Integer courseId,Integer rows, Integer page);

    List<Team> showMyTeam(Long username);

    Integer createTeam(Long leader,Integer courseId,Integer maxNum);

    Team getTeam(int teamId);

    void addAMember(int teamId, Long id);

    void updateAvailable(int teamId, Boolean status);

    void updateDisplay(int teamId, boolean status);

    Boolean existTeam(int teamId);
}
