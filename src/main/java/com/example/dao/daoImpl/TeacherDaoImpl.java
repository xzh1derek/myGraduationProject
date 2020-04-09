package com.example.dao.daoImpl;

import com.example.dao.ITeacherDao;
import com.example.domain.Teacher;
import com.example.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class TeacherDaoImpl implements ITeacherDao
{
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher getTeacher(Integer id)
    {
        return teacherMapper.getTeacher(id);
    }

    @Override
    public List<Teacher> findAllTeachers()
    {
        return teacherMapper.findAllTeachers();
    }

    /**
     * 增添一个老师，id使用填充方法得到
     */
    @Override
    public void createTeacher(Teacher teacher)
    {
        Set<Integer> idSet = teacherMapper.queryTeachersIdAsHashSet();
        int i;
        for(i=1;i<=64;i++)
        {
            if(!idSet.contains(i)) break;
        }
        teacher.setId(i);
        teacherMapper.createTeacher(teacher);
    }

    @Override
    public void updateTeacher(Teacher teacher)
    {
        teacherMapper.updateTeacher(teacher);
    }

    @Override
    public void deleteTeacher(Integer id)
    {
        teacherMapper.deleteTeacher(id);
    }

    @Override
    public Teacher queryTeacherByUsername(String username)
    {
        return teacherMapper.queryTeacherByUsername(username);
    }

    @Override
    public void updateIdentity(Integer id, String identity)
    {
        teacherMapper.updateIdentity(id,identity);
    }
}
