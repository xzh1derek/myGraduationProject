package com.example.service.serviceImpl;

import com.example.dao.IModuleDao;
import com.example.domain.Module;
import com.example.domain.Project;
import com.example.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleServiceImpl implements IModuleService
{
    @Autowired
    private IModuleDao moduleDao;

    @Override
    public Integer createProject(Project project)
    {
        return moduleDao.createProject(project);
    }

    @Override
    public List<Project> queryProjectWithModules(Integer teacher)
    {
        return moduleDao.queryProjectWithModules(teacher);
    }

    @Override
    public void createModule(Module module)
    {
        moduleDao.createModule(module);
    }
}
