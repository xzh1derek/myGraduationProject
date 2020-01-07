package com.example.service;

import com.example.domain.Module;
import com.example.domain.Project;
import com.example.domain.User;

import java.util.List;

public interface IModuleService
{
    void createProject(Project project);

    List<Project> queryProjectWithModules(Integer teacher);

    void createModule(Module module);

    void newUserModule(Long username,Integer module_id);

    void updateStuNum(Integer moduleId,Integer num);

    List<Module> queryModulesByTeacher(Integer teacher);

    List<Module> queryModulesByProject(Integer projectId);

    List<User> queryStudentsByModule(Integer moduleId);

    void deleteModule(Integer moduleId);

    void deleteModules(Integer projectId);

    void updateProject(Project project);

    void updateIsPublished(Integer projectId,Boolean status);

    void updateModule(Module module);

    Module getModule(Integer moduleId);

    Project getProject(Integer projectId);

    void addTwoClassesIntoModule(Integer moduleId, Integer class1, Integer class2);
}