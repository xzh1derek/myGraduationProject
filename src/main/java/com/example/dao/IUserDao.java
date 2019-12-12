package com.example.dao;

import com.example.domain.User;
import com.example.domain.UserCourse;

import java.util.List;

public interface IUserDao
{
    void updateLeader(Long num, boolean b, Integer courseId);

    void updateTeamId(Long num, Integer teamId, Integer courseId);

    User getUser(Long num);

    List<UserCourse> getUserCourses(Long u);

    User findAUser(Long num);

    Boolean existUser(Long num);

    UserCourse getUserCourse(Long u, Integer c);
}
