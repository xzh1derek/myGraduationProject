package com.example.mapper;
import com.example.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface UserMapper
{
    @Select("select * from user where username=#{u}")
    @Results(id="userMap",value = {
            @Result(id = true, column = "username", property = "username"),
            @Result(property = "userCourses", column = "username", many = @Many(select = "com.example.mapper.UserCourseMapper.getUserCourses",fetchType = FetchType.LAZY))
    })
    User getUser(Long u);

    @Select("select * from user where username=#{u}")
    User findAUser(Long u);

    @Select("select * from user where class_id=#{c}")
    List<User> findUsersByClass(Integer c);

    @Update("update user set qq=#{q} where username=#{u}")
    void updateQq(Long u, String q);
}
