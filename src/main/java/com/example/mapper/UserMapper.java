package com.example.mapper;

import com.example.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper
{
    @Update("update user set is_leader=#{b} where username=#{num}")
    void updateLeader(Long num, boolean b);

    @Update("update user set teamleader=#{teamleader} where username=#{num}")
    void updateTeam(Long num, Long teamleader);

    @Update("update user set invitation_id=#{invitation_id} where username=#{num}")
    void updateInvitation(Long num, Long invitation_id);

    @Update("update user set application_status=#{s} where username=#{num}")
    void updateApplication(Long num, String s);

    @Select("select * from user where username=#{num}")
    User findById(Long num);

    @Update("update user set is_leader=0, teamleader=0 where teamleader<>0")
    void deleteMembers();
}
