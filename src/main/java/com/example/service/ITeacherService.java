package com.example.service;

import com.example.domain.Teacher;

import java.util.List;

public interface ITeacherService
{
    Teacher getTeacher(Integer id);

    List<Teacher> findAllTeachers();

    void createTeacher(String username,String name);

    void updateTeacher(Integer id,String username,String name);

    void deleteTeacher(Integer id);
}
