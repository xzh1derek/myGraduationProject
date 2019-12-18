package com.example.mapper;

import com.example.domain.Project;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

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

    @Select("select * from project where teacher=#{teacher}")
    @Results(id="projectMap",value = {
            @Result(id=true, column = "id", property = "id"),
            @Result(column = "course_id", property = "course_id"),
            @Result(property = "course",column = "course_id", one = @One(select = "com.example.mapper.CourseMapper.getCourse",fetchType = FetchType.EAGER)),
            @Result(property = "modules", column = "id", many = @Many(select = "com.example.mapper.ModuleMapper.queryModulesByProject",fetchType = FetchType.EAGER))
    })
    List<Project> queryProjectWithModules(Integer teacher);
}
