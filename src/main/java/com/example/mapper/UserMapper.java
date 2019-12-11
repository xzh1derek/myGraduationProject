package com.example.mapper;
import com.example.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

@Mapper
public interface UserMapper
{
    @Select("select * from user where username=#{u}")
    @Results(id="userMap",value = {
            @Result(id = true, column = "username", property = "username"),
            @Result(property = "userCourses", column = "username", many = @Many(select = "com.example.mapper.UserCourseMapper.getUsers",fetchType = FetchType.LAZY))
    })
    User getUser(Long u);

    @Select("select * from user where username=#{u}")
    User findAUser(Long u);

    @Update("update user set qq=#{q} where username=#{u}")
    void updateQq(Long u, String q);
}
