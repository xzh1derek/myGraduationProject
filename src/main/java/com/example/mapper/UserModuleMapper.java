package com.example.mapper;

import com.example.domain.UserModule;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserModuleMapper
{
    @Insert("insert into user_module(username,module_id) values(#{username},#{module_id})")
    void newUserModule(Long username, Integer module_id);

    @Select("select * from user_module where module_id=#{moduleId}")
    List<UserModule> queryUserModuleByModule(Integer moduleId);
}
