package com.example.dao;

import com.example.domain.Module;
import com.example.domain.Project;

import java.util.List;

public interface IModuleDao
{
    Integer createProject(Project project);

    List<Project> queryProjectWithModules(Integer teacher);

    void createModule(Module module);
}
