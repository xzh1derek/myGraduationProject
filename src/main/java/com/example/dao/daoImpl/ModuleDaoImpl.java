package com.example.dao.daoImpl;

import com.example.dao.IModuleDao;
import com.example.domain.Module;
import com.example.domain.Project;
import com.example.mapper.ModuleMapper;
import com.example.mapper.ProjectMapper;
import com.example.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModuleDaoImpl implements IModuleDao
{
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private ModuleRepository moduleRepository;

    @Override
    public Integer createProject(Project project)
    {
        projectMapper.createProject(project);
        return project.getId();
    }

    @Override
    public List<Project> queryProjectWithModules(Integer teacher)
    {
        return projectMapper.queryProjectWithModules(teacher);
    }

    @Override
    public void createModule(Module module)
    {
        moduleMapper.createModule(module);
    }
}
