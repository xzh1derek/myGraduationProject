package com.example.service;

import com.example.domain.User;
import com.example.domain.UserCourse;

import java.util.List;

public interface IUserService
{
    void updateLeader(UserCourse userCourse);

    void updateTeamId(UserCourse userCourse, Integer teamId);

    User getUser(Long num);

    User findAUser(Long num);

    Boolean existUser(Long num);

    UserCourse getUserCourse(Long username, Integer courseId);

    List<UserCourse> getUserCourses(Long u);

    Boolean hasATeam(Long username,Integer courseId);

    Boolean userMatchCourse(Long username, Integer courseId);

    void updateQQ(Long u, String qq);

    List<User> findUsersByClass(Integer c);

    void updateMessage(Long u);
}
