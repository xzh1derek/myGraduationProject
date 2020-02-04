package com.example.service.serviceImpl;

import com.example.dao.IModuleDao;
import com.example.domain.Module;
import com.example.domain.Project;
import com.example.domain.User;
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
    public void createProject(Project project)
    {
        moduleDao.createProject(project);
    }

    @Override
    public List<Project> queryProjectWithModules()
    {
        return moduleDao.queryProjectWithModules();
    }

    @Override
    public Integer createModule(Module module)
    {
        return moduleDao.createModule(module);
    }

    @Override
    public void updateStuNum(Integer moduleId, Integer num)
    {
        moduleDao.updateStuNum(moduleId,num);
    }

    @Override
    public List<User> queryStudentsByModule(Integer moduleId)
    {
        return moduleDao.queryStudentsByModule(moduleId);
    }

    @Override
    public void deleteModule(Integer moduleId)
    {
        moduleDao.deleteModule(moduleId);
    }

    @Override
    public void deleteModules(Integer projectId)
    {
        moduleDao.deleteModules(projectId);
    }

    @Override
    public void updateProject(Project project)
    {
        moduleDao.updateProject(project);
    }

    @Override
    public void updateIsPublished(Integer projectId, Boolean status)
    {
        moduleDao.updateIsPublished(projectId,status);
    }

    @Override
    public void updateModule(Module module)
    {
        moduleDao.updateModule(module);
    }

    @Override
    public Module getModule(Integer moduleId)
    {
        return moduleDao.getModule(moduleId);
    }

    @Override
    public Project getProject(Integer projectId)
    {
        return moduleDao.getProject(projectId);
    }

    @Override
    public void addTwoClassesIntoModule(Integer moduleId, Integer class1, Integer class2)
    {
        moduleDao.addTwoClassesIntoModule(moduleId,class1,class2);
    }

    @Override
    public List<Module> queryModulesByProject(Integer projectId)
    {
        return moduleDao.queryModulesByProject(projectId);
    }

    @Override
    public void newUserModule(Long username, Integer module_id)
    {
        moduleDao.newUserModule(username,module_id);
    }

    @Override
    public List<Module> queryModuleOrderByDate(Long teacher)
    {
        return moduleDao.queryModuleOrderByDate(teacher);
    }

    @Override
    public List<Module> queryModuleAfterToday(Long teacher)
    {
        return moduleDao.queryModuleAfterToday(teacher);
    }

    @Override
    public List<Module> queryModuleOfArbitrary(Long teacher)
    {
        return moduleDao.queryModuleOfArbitrary(teacher);
    }

    @Override
    public List<Module> queryModuleByUsername(Long userId)
    {
        return moduleDao.queryModuleByUsername(userId);
    }

    @Override
    public List<Module> queryModuleByUsernameAfterToday(Long userId)
    {
        return moduleDao.queryModuleByUsernameAfterToday(userId);
    }

    @Override
    public List<Module> queryModuleExpired()
    {
        return moduleDao.queryModuleExpired();
    }

    @Override
    public void deleteUserModuleByModule(Integer moduleId)
    {
        moduleDao.deleteUserModuleByModule(moduleId);
    }
}
