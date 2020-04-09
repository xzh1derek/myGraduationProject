package com.example.dao;

import com.example.domain.Teacher;

import java.util.List;

public interface ITeacherDao
{
    Teacher getTeacher(Integer id);

    List<Teacher> findAllTeachers();

    void createTeacher(Teacher teacher);

    void updateTeacher(Teacher teacher);

    void deleteTeacher(Integer id);

    Teacher queryTeacherByUsername(String username);

    void updateIdentity(Integer id,String identity);
}
