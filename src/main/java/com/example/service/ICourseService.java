package com.example.service;

import com.example.domain.Course;

import java.util.List;

public interface ICourseService
{
    Integer newCourse(String code,String name,Float credit,Integer hours,Integer teachers,Boolean is_team,Integer max_num);

    void newUserCourse(Long username,Integer courseId,Integer hours);

    void updateStuNum(Integer courseId, Integer num);

    Course getCourse(Integer courseId);

    List<Course> queryCourseList();

    void updateCourse(Integer courseId,String code,String name,Float credit,Integer hours,Integer teachers,Boolean is_team,Integer max_num);

    void deleteCourse(Integer courseId);
}
