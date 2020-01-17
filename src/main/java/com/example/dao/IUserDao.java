package com.example.dao;

import com.example.domain.User;
import com.example.domain.UserCourse;

import java.util.List;

public interface IUserDao
{
    void updateIsLeader(UserCourse userCourse);

    void updateTeamId(UserCourse userCourse);

    User getUser(Long num);

    List<UserCourse> getUserCourses(Long u);

    User findAUser(Long num);

    void deleteTeamMembers(Integer teamId);

    Boolean existUser(Long num);

    UserCourse getUserCourse(Long u, Integer c);

    void updateQQ(Long u, String qq);

    List<Long> findUsersByClass(Integer c);

    void saveUser(User user);

    void updateMessage(Long u);

    void newUser(User user);

    void deleteUser(Long username);

    void updateUser(Long userId, User user);

    List<User> queryUserListPaging(Integer rows,Integer page);

    Integer queryUserNumbers();

    List<Long> queryUsernameByTeamId(Integer teamId);
}
