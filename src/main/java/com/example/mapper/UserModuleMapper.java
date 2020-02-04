package com.example.mapper;

import com.example.domain.UserModule;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserModuleMapper
{
    @Insert("insert into user_module(username,module_id) values(#{username},#{module_id})")
    void newUserModule(Long username, Integer module_id);

    @Select("select username from user_module where module_id=#{moduleId}")
    List<Long> queryUsernameByModule(Integer moduleId);

    @Select("select count(username) from user_module where module_id=#{moduleId}")
    Integer countUserNumbersByModule(Integer moduleId);

    @Select("select module_id from user_module where username=#{userId}")
    List<Integer> queryModuleIdByUsername(Long userId);

    @Delete("delete from user_module where module_id=#{moduleId}")
    void deleteUserModuleByModule(Integer moduleId);
}
