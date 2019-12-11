package com.example.service;

import com.example.domain.User;
import com.example.domain.UserCourse;

import java.util.List;

public interface IUserService
{
    void updateLeader(Long num,boolean b,int courseId);

    void updateTeamId(Long num,int teamId,int courseId);

    User getUser(Long num);

    User findAUser(Long num);

    Boolean existUser(Long num);

    UserCourse getUserCourse(Long username, Integer courseId);

    Boolean hasATeam(Long username,Integer courseId);

    Boolean userMatchCourse(Long username, Integer courseId);
}
