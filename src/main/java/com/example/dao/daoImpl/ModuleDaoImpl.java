package com.example.dao.daoImpl;

import com.example.dao.IModuleDao;
import com.example.domain.Project;
import com.example.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModuleDaoImpl implements IModuleDao
{
    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public Integer createProject(Project project)
    {
        projectMapper.createProject(project);
        return project.getId();
    }
}
