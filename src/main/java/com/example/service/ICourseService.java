package com.example.service;

import com.example.domain.Course;
import com.example.domain.Project;
import com.example.domain.UserCourse;

import java.util.List;

public interface ICourseService
{
    Integer newCourse(String code,String name,Float credit,Integer hours,Integer teachers,Boolean is_team,Integer max_num);

    void newUserCourse(UserCourse userCourse);

    void updateStuNum(Integer courseId, Integer num);

    Course getCourse(Integer courseId);

    List<Course> queryCourseList();

    void updateCourse(Integer courseId,String code,String name,Float credit,Integer hours,Integer teachers,Boolean is_team,Integer max_num);

    void deleteCourse(Integer courseId);

    List<Course> queryCoursesWithProjects(Integer teacher);

    List<Project> queryProjectByCourse(Integer courseId);

    void deleteProjects(Integer courseId);

    void newClassCourse(Integer classId,Integer courseId);

    void deleteUserCourse(Integer courseId);

    void deleteClassCourse(Integer courseId);

    List<Course> queryCourseWithClasses();
}
