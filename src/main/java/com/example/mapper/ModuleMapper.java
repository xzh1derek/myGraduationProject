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

    @Insert("insert into module(module_index,project_id,location,time,stu_num) values(#{module_index},#{project_id},#{location},#{time},#{stu_num})")
    void createModule(Module module);

    @Update("update module set stu_num=#{num} where id=#{moduleId}")
    void updateStuNum(Integer moduleId,Integer num);

    @Update("update module set module_index=#{module_index},location=#{location},time=#{time},stu_num=#{stu_num} where id=#{id}")
    void updateModule(Module module);

    @Select("select * from module")
    @Results(id="moduleMap",value = {
            @Result(column = "project_id", property = "project_id"),
            @Result(property = "project",column = "project_id", one = @One(select = "com.example.mapper.ProjectMapper.queryProjectWithCourse",fetchType = FetchType.EAGER)),
    })
    List<Module> queryModuleWithProjectAndCourse();

    @Delete("delete from module where project_id=#{projectId}")
    void deleteModules(Integer projectId);
}
