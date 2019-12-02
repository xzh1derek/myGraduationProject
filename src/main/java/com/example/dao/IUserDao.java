package com.example.dao;

import com.example.domain.User;

import java.util.List;

public interface IUserDao
{
    void updateLeader(Long num, boolean b);

    void updateTeam(Long num, Long teamleader);

    void updateInvitation(Long num, Long invitationId);

    User getUser(Long num);

    void updateApplication(Long num, String s);

    List<User> findByTeamleader(Long leader);

    Boolean existUser(Long num);

    void saveUser(String username, String password, String name, String school, String qq);
}
