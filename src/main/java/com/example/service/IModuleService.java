package com.example.service;

import com.example.domain.Module;
import com.example.domain.Project;

import java.util.List;

public interface IModuleService
{
    Integer createProject(Project project);

    List<Project> queryProjectWithModules(Integer teacher);

    void createModule(Module module);
}
