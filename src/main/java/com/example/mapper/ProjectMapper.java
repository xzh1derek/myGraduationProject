package com.example.mapper;

import com.example.domain.Project;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface ProjectMapper
{
    @Insert("insert into project(project_name,project_index,course_id,hours,is_fixed,is_arranged) values(#{project_name},#{project_index},#{course_id},#{hours},#{is_fixed},0)")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void createProject(Project project);

    @Update("update project set is_published=#{status} where id=#{projectId}")
    void updateIsPublished(Integer projectId,Boolean status);

    @Update("update project set project_name=#{project_name},is_fixed=#{is_fixed},hours=#{hours} where id=#{id}}")
    void updateProject(Project project);

    @Select("select * from project where course_id=#{courseId}")
    List<Project> queryProjectsByCourse(Integer courseId);

    @Select("select * from project where id=#{id}")
    Project getProject(Integer id);

    @Select("select * from project where id=#{id}")
    @Results(id="projectMapWithCourse",value = {
            @Result(column = "course_id", property = "course_id"),
            @Result(property = "course",column = "course_id", one = @One(select = "com.example.mapper.CourseMapper.getCourse",fetchType = FetchType.EAGER))
    })
    Project queryProjectWithCourse(Integer id);

    @Delete("delete from project where id=#{projectId}")
    void deleteProject(Integer projectId);

    @Delete("delete from project where course_id=#{courseId}")
    void deleteProjects(Integer courseId);

    @Select("select * from project")
    @Results(id="projectMapWithCourseAndModules",value = {
            @Result(id=true, column = "id", property = "id"),
            @Result(column = "course_id", property = "course_id"),
            @Result(property = "course",column = "course_id", one = @One(select = "com.example.mapper.CourseMapper.getCourse",fetchType = FetchType.EAGER)),
            @Result(property = "modules", column = "id", many = @Many(select = "com.example.mapper.ModuleMapper.queryModulesByProject",fetchType = FetchType.LAZY))
    })
    List<Project> queryProjectWithModules();

}
