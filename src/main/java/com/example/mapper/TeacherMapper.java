package com.example.mapper;

import com.example.domain.Teacher;
import org.apache.ibatis.annotations.*;
import java.util.LinkedHashSet;
import java.util.List;

@Mapper
public interface TeacherMapper
{
    @Select("select * from teacher where id=#{id}")
    Teacher getTeacher(Integer id);

    @Select("select * from teacher where username=#{username}")
    Teacher queryTeacherByUsername(String username);

    @Select("select * from teacher")
    List<Teacher> findAllTeachers();

    @Select("select id from teacher")
    LinkedHashSet<Integer> queryTeachersIdAsHashSet();

    @Insert("insert into teacher(id,username,name,identity) value(#{id},#{username},#{name},教师)")
    void createTeacher(Teacher teacher);

    @Update("update teacher set username=#{username},name=#{name} where id=#{id}")
    void updateTeacher(Teacher teacher);

    @Delete("delete from teacher where id=#{id}")
    void deleteTeacher(Integer id);

    @Update("update teacher set identity=#{identity} where id=#{id}")
    void updateIdentity(Integer id,String identity);
}
