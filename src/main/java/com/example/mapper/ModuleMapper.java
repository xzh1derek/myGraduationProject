package com.example.mapper;

import com.example.domain.Module;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface ModuleMapper
{
    @Select("select * from module where project_id=#{id}")
    List<Module> queryModulesByProject(Integer id);

    @Insert("insert into module(module_index,project_id,location,date,time,stu_num) values(#{module_index},#{project_id},#{location},#{date},#{time},#{stu_num})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void createModule(Module module);

    @Update("update module set stu_num=#{num} where id=#{moduleId}")
    void updateStuNum(Integer moduleId,Integer num);

    @Update("update module set module_index=#{module_index},location=#{location},date=#{date},date_of_string=#{dateOfString},time=#{time},stu_num=#{stu_num} where id=#{id}")
    void updateModule(Module module);

    @Select("select * from module where project_id in (select distinct project.id from project where is_published=true and course_id in (select distinct course.id from course where course.teacher&#{teacher}=#{teacher})) order by date")
    @Results(id="moduleMap",value = {
            @Result(column = "project_id", property = "project_id"),
            @Result(property = "project",column = "project_id", one = @One(select = "com.example.mapper.ProjectMapper.queryProjectWithCourse",fetchType = FetchType.EAGER)),
    })
    List<Module> queryModuleOrderByDate(Long teacher);

    @Select("select * from module where date>=curdate() and project_id in (select distinct project.id from project where is_published=true and course_id in (select distinct course.id from course where course.teacher&#{teacher}=#{teacher})) order by date")
    @ResultMap(value = "moduleMap")
    List<Module> queryModuleAfterToday(Long teacher);

    @Delete("delete from module where project_id=#{projectId}")
    void deleteModules(Integer projectId);

    @Select("select * from module where project_id in (select distinct project.id from project where is_published=true and is_fixed=false and course_id in (select distinct course.id from course where course.teacher&#{teacher}=#{teacher})) order by date")
    @ResultMap(value = "moduleMap")
    List<Module> queryModuleOfArbitrary(Long teacher);

    @Select("select * from module where id in (select module_id from user_module where username=#{userId}) order by date")
    @ResultMap(value = "moduleMap")
    List<Module> queryModuleByUsername(Long userId);

    @Select("select * from module where date>=curdate() and id in (select module_id from user_module where username=#{userId}) order by date")
    @ResultMap(value = "moduleMap")
    List<Module> queryModuleByUsernameAfterToday(Long userId);

    @Select("select * from module where date<curdate()")
    List<Module> queryModuleExpired();
}
