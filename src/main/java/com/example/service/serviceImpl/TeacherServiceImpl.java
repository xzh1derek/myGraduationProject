package com.example.service.serviceImpl;

import com.example.config.utils.ByteConverter;
import com.example.dao.ITeacherDao;
import com.example.domain.Teacher;
import com.example.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl implements ITeacherService
{
    @Autowired
    private ITeacherDao teacherDao;

    @Override
    public Teacher getTeacher(Integer id)
    {
        return teacherDao.getTeacher(id);
    }

    @Override
    public List<Teacher> findAllTeachers()
    {
        return teacherDao.findAllTeachers();
    }

    @Override
    public void createTeacher(String username, String name)
    {
        Teacher teacher = new Teacher();
        teacher.setUsername(username);
        teacher.setName(name);
        teacherDao.createTeacher(teacher);
    }

    @Override
    public void deleteTeacher(Integer id)
    {
        teacherDao.deleteTeacher(id);
    }

    @Override
    public Teacher queryTeacherByUsername(String username)
    {
        return teacherDao.queryTeacherByUsername(username);
    }

    @Override
    public void updateIdentity(Integer id, String identity)
    {
        teacherDao.updateIdentity(id,identity);
    }
}
