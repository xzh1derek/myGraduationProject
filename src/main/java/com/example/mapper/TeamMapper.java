package com.example.mapper;

import com.example.domain.Team;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import java.util.List;

@Mapper
public interface TeamMapper
{
    @Select("select * from team where is_display=1 limit #{x} offset #{y}")
    @Results(id="teamMap",value = {
            @Result(column = "leader", property = "leader"),
            @Result(column = "course_id", property = "course_id"),
            @Result(property = "leaderDetail",column = "leader",one = @One(select = "com.example.mapper.UserMapper.findAUser",fetchType = FetchType.LAZY)),
            @Result(property = "course",column = "course_id",one = @One(select = "com.example.mapper.CourseMapper.getCourse",fetchType = FetchType.LAZY))
    })
    List<Team> queryTeamList(Integer x, Integer y);

    @Select("select * from team where leader=#{num}")
    @ResultMap(value = "teamMap")
    List<Team> queryTeamsByLeader(Long num);

    @Select("select * from team where course_id=#{c} limit #{x} offset #{y}")
    @ResultMap(value = "teamMap")
    List<Team> queryTeamsByCourse(Integer courseId,Integer x,Integer y);

    @Insert("insert into team(leader,course_id,max_num) values(#{leader},#{course_id},#{max_num})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void createTeam(Team team);

    @Update("update team set available=#{status} where id=#{teamId}")
    void updateAvailable(int teamId, boolean status);

    @Update("update team set is_display=#{status} where id=#{teamId}")
    void updateDisplay(int teamId, boolean status);

    @Delete("delete from team")
    void deleteTeams();
}
