package com.example.service;

import com.example.domain.Teacher;

import java.util.List;

public interface ITeacherService
{
    Teacher getTeacher(Integer id);

    List<Teacher> findAllTeachers();

    void createTeacher(String username,String name);

    void deleteTeacher(Integer id);

    Teacher queryTeacherByUsername(String username);

    void updateIdentity(Integer id,String identity);
}
