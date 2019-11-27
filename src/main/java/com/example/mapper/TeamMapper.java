package com.example.mapper;

import com.example.domain.Team;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface TeamMapper
{
    @Select("select * from team where is_display=1")
    @Results(id="teamMap",value = {
            @Result(id = true, column = "leader", property = "leader"),
            @Result(property = "user", column = "leader", one = @One(select = "com.example.mapper.UserMapper.findById",fetchType = FetchType.EAGER))
    })
    List<Team> queryTeamList();

    @Select("select * from team where leader=#{num}")
    @ResultMap(value = "teamMap")
    List<Team> queryATeam(Long num);

    @Insert("insert into team(leader) values(#{leader})")
    void createTeam(Long leader);

    @Update("update team set available=#{status} where leader=#{leader}")
    void updateAvailable(Long leader, boolean status);

    @Update("update team set is_display=#{status} where leader=#{leader}")
    void updateDisplay(Long leader, boolean status);

    @Delete("delete from team")
    void deleteTeams();

}
