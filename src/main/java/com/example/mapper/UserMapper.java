package com.example.mapper;

import com.example.domain.Team;
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
            @Result(property = "userCourses", column = "username", many = @Many(select = "com.example.mapper.UserMapper.findUsers",fetchType = FetchType.LAZY))
    })
    User queryAUser(Long u);

    @Select("select * from user_course where username=#{u)")
    List<User> findUsers(Long u);

    @Update("update user_course set is_leader=#{b} where username=#{num} and course_id=#{c}")
    void updateLeader(Long num, Integer c, boolean b);

    @Update("update user_course set teamleader=#{teamleader} where username=#{num} and course_id=#{c}")
    void updateTeam(Long num, Integer c, Long teamleader);

    @Update("update user_course set invitation_id=#{invitation_id} where username=#{num} and course_id=#{c}")
    void updateInvitation(Long num, Integer c, Long invitation_id);

    @Update("update user_course set application_status=#{s} where username=#{num} and course_id=#{c}")
    void updateApplication(Long num, Integer c, String s);

    @Select("select * from user where username=#{num}")
    User findById(Long num);

    @Update("update user_course set is_leader=0, teamleader=0, application_status=null where teamleader<>0")
    void deleteMembers();

    @Update("update user_course set invitation_id=0, application_status=null where invitation_id<>0")
    void deleteInvitations();

    @Update("update user set qq=#{q} where username=#{u}")
    void updateQq(Long u, String q);
}
