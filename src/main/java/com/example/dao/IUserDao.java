package com.example.dao;

import com.example.domain.User;
import com.example.domain.UserCourse;

import java.util.List;

public interface IUserDao
{
    void saveUserCourse(UserCourse userCourse);

    User getUser(Long num);

    List<UserCourse> getUserCourses(Long u);

    User findAUser(Long num);

    Boolean existUser(Long num);

    UserCourse getUserCourse(Long u, Integer c);

    void updateQQ(Long u, String qq);

    List<User> findUsersByClass(Integer c);

    void saveUser(User user);

    void updateMessage(Long u);
}
