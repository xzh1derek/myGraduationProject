package com.example.mapper;
import com.example.config.sql.StudentProvider;
import com.example.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

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

    @SelectProvider(type = StudentProvider.class ,method = "sqlStudent")
    List<User> queryStudentsDynamically(Map<String,String> map);

    @SelectProvider(type = StudentProvider.class ,method = "sqlStudentRecords")
    Integer queryStudentsRecords(Map<String,String> map);

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

    @Update("update user set username=#{user.username},name=#{user.name},class_id=#{user.class_id},school=#{user.school},year=#{user.year} where username=#{userId}")
    void updateUser(Long userId, User user);

    @Delete("delete from user")
    void deleteAllUsers();

    @Select("select * from user limit #{x} offset #{y}")
    List<User> queryUserListPaging(Integer x,Integer y);

    @Select("select count(*) from user")
    Integer queryUserNumbers();
}
