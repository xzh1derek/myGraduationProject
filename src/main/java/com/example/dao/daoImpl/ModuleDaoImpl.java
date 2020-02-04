package com.example.dao.daoImpl;

import com.example.dao.IModuleDao;
import com.example.domain.Module;
import com.example.domain.Project;
import com.example.domain.User;
import com.example.domain.UserModule;
import com.example.mapper.*;
import com.example.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ModuleDaoImpl implements IModuleDao
{
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private UserModuleMapper userModuleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ModuleRepository moduleRepository;

    @Override
    public void createProject(Project project)
    {
        projectMapper.createProject(project);
    }

    @Override
    public List<Project> queryProjectWithModules()
    {
        return projectMapper.queryProjectWithModules();
    }

    @Override
    public Integer createModule(Module module)
    {
        moduleMapper.createModule(module);
        return module.getId();
    }

    @Override
    public List<Module> queryModulesByProject(Integer projectId)
    {
        return moduleMapper.queryModulesByProject(projectId);
    }

    @Override
    public void updateStuNum(Integer moduleId, Integer num)
    {
        moduleMapper.updateStuNum(moduleId,num);
    }

    @Override
    public List<User> queryStudentsByModule(Integer moduleId)
    {
        List<Long> usernames = userModuleMapper.queryUsernameByModule(moduleId);
        List<User> userList = new ArrayList<>();
        for(Long username : usernames)
        {
            userList.add(userMapper.findAUser(username));
        }
        return userList;
    }

    @Override
    public void deleteModule(Integer moduleId)
    {
        moduleRepository.deleteById(moduleId);
    }

    @Override
    public void deleteModules(Integer projectId)
    {
        moduleMapper.deleteModules(projectId);
    }

    @Override
    public void updateProject(Project project)
    {
        projectMapper.updateProject(project);
    }

    @Override
    public void updateIsPublished(Integer projectId, Boolean status)
    {
        projectMapper.updateIsPublished(projectId,status);
    }

    @Override
    public void updateModule(Module module)
    {
        moduleMapper.updateModule(module);
    }

    @Override
    public Module getModule(Integer moduleId)
    {
        return moduleRepository.getOne(moduleId);
    }

    @Override
    public Project getProject(Integer projectId)
    {
        return projectMapper.getProject(projectId);
    }

    @Override
    public void addTwoClassesIntoModule(Integer moduleId, Integer class1, Integer class2)
    {
        Module module = moduleRepository.getOne(moduleId);
        module.setClass1(class1);
        module.setClass2(class2);
        moduleRepository.save(module);
    }

    @Override
    public void newUserModule(Long username, Integer module_id)
    {
        userModuleMapper.newUserModule(username,module_id);
    }

    @Override
    public List<Module> queryModuleOrderByDate(Long teacher)
    {
        return moduleMapper.queryModuleOrderByDate(teacher);
    }

    @Override
    public List<Module> queryModuleAfterToday(Long teacher)
    {
        return moduleMapper.queryModuleAfterToday(teacher);
    }

    @Override
    public Integer countUserNumbersByModule(Integer moduleId)
    {
        return userModuleMapper.countUserNumbersByModule(moduleId);
    }

    @Override
    public List<Module> queryModuleOfArbitrary(Long teacher)
    {
        return moduleMapper.queryModuleOfArbitrary(teacher);
    }

    @Override
    public List<Module> queryModuleByUsername(Long userId)
    {
        return moduleMapper.queryModuleByUsername(userId);
    }

    @Override
    public List<Module> queryModuleByUsernameAfterToday(Long userId)
    {
        return moduleMapper.queryModuleByUsernameAfterToday(userId);
    }

    @Override
    public List<Module> queryModuleExpired()
    {
        return moduleMapper.queryModuleExpired();
    }

    @Override
    public void deleteUserModuleByModule(Integer moduleId)
    {
        userModuleMapper.deleteUserModuleByModule(moduleId);
    }
}
