package com.example.dao;

import com.example.domain.Module;
import com.example.domain.Project;
import com.example.domain.User;
import com.example.domain.UserModule;

import java.util.List;

public interface IModuleDao
{
    void createProject(Project project);

    List<Project> queryProjectWithModules(Integer teacher);

    List<Module> queryModulesByProject(Integer projectId);

    void createModule(Module module);

    void updateProject(Project project);

    void updateIsPublished(Integer projectId,Boolean status);

    void updateModule(Module module);

    void newUserModule(Long username,Integer module_id);

    void updateStuNum(Integer moduleId,Integer num);

    List<Module> queryModulesByTeacher(Integer teacher);

    List<User> queryStudentsByModule(Integer moduleId);

    void deleteModule(Integer moduleId);

    void deleteModules(Integer projectId);

    Module getModule(Integer moduleId);

    Project getProject(Integer projectId);

    void addTwoClassesIntoModule(Integer moduleId, Integer class1, Integer class2);
}
