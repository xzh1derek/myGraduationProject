package com.example.dao;

import com.example.domain.User;
import com.example.domain.UserCourse;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

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

    UserCourse queryUserCourse(Long u, Integer c);

    UserCourse queryUserCourseWithCourse(Long u, Integer c);

    void updateQQ(Long u, String qq);

    List<Long> findUsersByClass(Integer c);

    void saveUser(User user);

    void newUser(User user);

    void deleteUser(Long username);

    void updateUser(Long userId, User user);

    List<User> queryUserListPaging(Integer rows,Integer page);

    Integer queryUserNumbers();

    List<Long> queryUsernameByTeamId(Integer teamId);

    List<User> sqlStudent(Map<String,String> map);

    Integer queryStudentsRecords(Map<String,String> map);

    void updateScore(Long username,Integer courseId,Float score,String teacher);

    List<UserCourse> queryUserCourseDynamically(Map<String,String> map);

    Integer queryUserCourseRecords(Map<String,String> map);

    Map<String,Object> queryScoreData(Integer courseId);
}
