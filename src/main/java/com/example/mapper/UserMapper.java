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

    @Select("select username from user where class_id=#{c}")
    List<Long> findUsersByClass(Integer c);

    @Update("update user set qq=#{q} where username=#{u}")
    void updateQq(Long u, String q);

    @Update("update user set new_message=0 where username=#{u}")
    void updateMessage(Long u);

    @Insert("insert into user(username,name,class_id,school,year) values(#{username},#{name},#{class_id},#{school},#{year})")
    void newUser(User user);

    @Delete("delete from user where username=#{username}")
    void deleteUser(Long username);

    @Update("update user set name=#{name},class_id=#{class_id},school=#{school},year=#{year} where username=#{userId}")
    void updateUser(Long userId, User user);

    @Delete("delete from user")
    void deleteAllUsers();

    @Select("select * from user limit #{x} offset #{y}")
    List<User> queryUserListPaging(Integer x,Integer y);
}
