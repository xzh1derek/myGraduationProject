package com.example.mapper;

import com.example.domain.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProjectMapper
{
    @Insert("insert into project(project_name,project_index,course_id,hours,is_fixed,teachers) values(#{project_name},#{project_index},#{course_id},#{hours},#{is_fixed},#{teachers})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void createProject(Project project);

    @Select("select * from project where course_id=#{courseId}")
    List<Project> queryProjectsByCourse(Integer courseId);

    @Select("select * from project where id=#{id}")
    Project getProject(Integer id);

    @Delete("delete from project where course_id=#{courseId}")
    void deleteProjects(Integer courseId);
}
