package com.example.service;

import com.example.domain.User;

import java.util.List;

public interface IUserService
{
    void updateLeader(Long num,boolean b);

    void updateTeam(Long num,Long teamleader);

    void updateInvitation(Long num, Long invitationId);

    User getUser(Long num);

    void updateApplication(Long num,String s);

    List<User> findByTeamleader(Long leader);

    Boolean existUser(Long num);

    void saveUser(Long username, String password, String name, String school, String qq);
}
