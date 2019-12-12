package com.example.mapper;
import com.example.domain.UserCourse;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface UserCourseMapper
{
    @Select("select * from user_course where username=#{u}")
    @Results(id="courseMap",value = {
            @Result(column = "course_id", property = "course_id"),
            @Result(property = "course", column = "course_id", one = @One(select = "com.example.mapper.CourseMapper.getCourse",fetchType = FetchType.EAGER))
    })
    List<UserCourse> getUserCourses(Long u);

    @Select("select * from user_course where username=#{u}")
    List<UserCourse> findUsers(Long u);

    @Select("select * from user_course where username=#{u} and course_id=#{c}")
    UserCourse getUserCourse(Long u, Integer c);

    @Update("update user_course set is_leader=#{b} where username=#{num} and course_id=#{c}")
    void updateLeader(Long num, Integer c, Boolean b);

    @Update("update user_course set team_id=#{team_id} where username=#{num} and course_id=#{c}")
    void updateTeamId(Long num, Integer c, Integer team_id);

    @Update("update user_course set is_leader=0, team_id=0 where team_id<>0")
    void deleteMembers();
}
