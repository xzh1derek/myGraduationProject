package com.example.mapper;

import com.example.domain.UserModule;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserModuleMapper
{
    @Insert("insert into user_module(username,module_id) values(#{username},#{module_id})")
    void newUserModule(Long username, Integer module_id);

    @Update("update user_module set teacher=#{teacher},is_team=#{is_team},is_fixed=#{is_fixed} where module_id=#{module_id}")
    void updateUserModule(Integer module_id,Long teacher,Boolean is_team,Boolean is_fixed);

    @Select("select * from user_module where module_id=#{moduleId}")
    List<UserModule> queryUserModuleByModule(Integer moduleId);
}
