package com.example.mapper;

import com.example.domain.Module;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ModuleMapper
{
    @Select("select * from module where project_id=#{id}")
    List<Module> queryModulesByProject(Integer id);

    @Insert("insert into module(module_index,project_id,location,time,stu_num) values(#{module_index},#{project_id},#{location},#{time},#{stu_num})")
    void createModule(Module module);
}
