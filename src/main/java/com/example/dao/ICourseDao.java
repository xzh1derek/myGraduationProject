package com.example.dao;

import com.example.domain.*;

import java.util.List;

public interface ICourseDao
{
    Integer newCourse(String code,String name,Float credit,Integer hours,Boolean is_team,Integer max_num);

    Course getCourse(Integer courseId);

    void newUserCourse(UserCourse userCourse);

    void updateTeachers(Integer courseId,Long teacher);

    void updateStuNum(Integer courseId,Integer num);

    List<Course> queryCourseList();

    void updateCourse(Course course);

    void deleteCourse(Integer courseId);

    List<Course> queryCoursesWithProjects();

    List<Project> queryProjectByCourse(Integer courseId);

    void deleteProject(Integer projectId);

    void deleteProjects(Integer courseId);

    void newClassCourse(Integer classId,Integer courseId);

    void deleteUserCourse(Integer courseId);

    void deleteClassCourse(Integer courseId);

    List<Course> queryCourseWithClasses();

    List<UserCourse> queryStudentsByCoursePaging(Integer courseId,Integer rows, Integer page);

    List<UserCourse> queryStudentsByCourse(Integer courseId);

    List<School> querySchoolWithClassesLimited(Integer courseId);

    List<Integer> queryClassByCourse(Integer course_id);
}
