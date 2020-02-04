package com.example.service;

import com.example.domain.User;
import com.example.domain.UserCourse;

import java.util.List;

public interface IUserService
{
    void updateIsLeader(Long username, Integer courseId, Boolean isLeader);

    void updateTeamId(Long username, Integer courseId, Integer teamId);

    User getUser(Long num);

    User findAUser(Long num);

    Boolean existUser(Long num);

    UserCourse getUserCourseWithUser(Long username, Integer courseId);

    UserCourse queryUserCourseWithCourse(Long username, Integer courseId);

    List<UserCourse> getUserCourses(Long u);

    Boolean hasATeam(Long username,Integer courseId);

    Boolean userMatchCourse(Long username, Integer courseId);

    void updateQQ(Long u, String qq);

    List<Long> findUsersByClass(Integer c);

    List<Long> queryUsernameByTeamId(Integer teamId);

    void updateMessage(Long u);

    void newUser(User user);

    void deleteUser(Long username);

    void updateUser(Long userId, User user);

    List<User> queryUserListPaging(Integer rows,Integer page);

    Integer queryUserNumbers();
}
