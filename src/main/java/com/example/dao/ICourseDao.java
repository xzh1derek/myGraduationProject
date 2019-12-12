package com.example.dao;

import com.example.domain.Course;
import com.example.domain.UserCourse;

public interface ICourseDao
{
    Integer newCourse(String code,String name,Float credit,Integer hours,Integer teachers,Boolean is_team,Integer max_num);

    Course getCourse(Integer courseId);

    void newUserCourse(UserCourse userCourse);

    void updateStuNum(Integer courseId,Integer num);
}
