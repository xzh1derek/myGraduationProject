package com.example.service;

import com.example.domain.Module;
import com.example.domain.Project;
import com.example.domain.User;

import java.util.List;

public interface IModuleService
{
    void createProject(Project project);

    List<Project> queryProjectWithModules();

    void createModule(Module module);

    void updateStuNum(Integer moduleId,Integer num);

    List<Module> queryModulesWithProjectAndCourse();

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

    void newUserModule(Long username,Integer module_id);

    void updateUserModule(Integer module_id,Long teacher,Boolean is_team,Boolean is_fixed);
}
