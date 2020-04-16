package com.example.mapper;
import com.example.config.sql.StudentProvider;
import com.example.config.sql.UserCourseProvider;
import com.example.domain.User;
import com.example.domain.UserCourse;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserCourseMapper
{
    @Select("select * from user_course where username=#{u}")
    @Results(id="userCourseMapWithCourse",value = {
            @Result(column = "course_id", property = "course_id"),
            @Result(property = "course", column = "course_id", one = @One(select = "com.example.mapper.CourseMapper.getCourse",fetchType = FetchType.EAGER))
    })
    List<UserCourse> getUserCourses(Long u);

    @Select("select * from user_course where username=#{u}")
    List<UserCourse> findUsers(Long u);

    @Select("select * from user_course where username=#{u} and course_id=#{c}")
    @Results(id="userCourseMapWithUser",value = {
            @Result(column = "username",property = "username"),
            @Result(column = "username",property = "user",one = @One(select = "com.example.mapper.UserMapper.findAUser",fetchType = FetchType.EAGER))
    })
    UserCourse getUserCourse(Long u, Integer c);

    @Select("select * from user_course where username=#{u} and course_id=#{c}")
    UserCourse queryUserCourse(Long u, Integer c);

    @Select("select * from user_course where username=#{u} and course_id=#{c}")
    @ResultMap(value = "userCourseMapWithCourse")
    UserCourse queryUserCourseWithCourse(Long u, Integer c);

    @Update("update user_course set is_leader=#{is_leader} where username=#{username} and course_id=#{course_id}")
    void updateIsLeader(UserCourse userCourse);

    @Update("update user_course set team_id=#{team_id} where username=#{username} and course_id=#{course_id}")
    void updateTeamId(UserCourse userCourse);

    @Update("update user_course set score=#{score},teacher=#{teacher} where username=#{username} and course_id=#{courseId}")
    void updateScore(Long username,Integer courseId,Float score,String teacher);

    @Update("update user_course set is_leader=false, team_id=0 where team_id<>0")
    void deleteMembers();

    @Select("select username from user_course where team_id=#{teamId}")
    List<Long> queryUsernameByTeamId(Integer teamId);

    @Update("update user_course set is_leader=false, team_id=0 where team_id=#{teamId}")
    void deleteTeamMembers(Integer teamId);

    @Delete("delete from user_course where course_id=#{courseId}")
    void deleteUserCourse(Integer courseId);

    @Insert("insert into user_course(username,course_id) values(#{username},#{course_id})")
    void newCourseList(UserCourse userCourse);

    @Select("select * from user_course where course_id=#{courseId} limit #{x} offset #{y}")
    @ResultMap(value = "userCourseMapWithUser")
    List<UserCourse> queryUserCourseByCoursePaging(Integer courseId,Integer x,Integer y);

    @Select("select * from user_course where course_id=#{courseId}")
    @ResultMap(value = "userCourseMapWithUser")
    List<UserCourse> queryUserCourseByCourse(Integer courseId);

    @Delete("delete from user_course")
    void deleteAllUserCourses();

    @Select("select count(course_id) from user_course where course_id=#{courseId}")
    Integer queryUserCourseNumbers(Integer courseId);

    @Select("select * from user_course where course_id=#{courseId} and team_id=0")
    @ResultMap(value = "userCourseMapWithUser")
    List<UserCourse> queryStudentsTeamless(Integer courseId);

    @SelectProvider(type = UserCourseProvider.class ,method = "sqlUserCourse")
    @ResultMap(value = "userCourseMapWithUser")
    List<UserCourse> sqlUserCourse(Map<String,String> map);

    @SelectProvider(type = UserCourseProvider.class ,method = "sqlUserCourseRecords")
    Integer sqlUserCourseRecords(Map<String,String> map);

    @Select("select score from user_course where course_id = #{courseId} and score is not null")
    List<Float> queryScoreNotNull(Integer courseId);
}
