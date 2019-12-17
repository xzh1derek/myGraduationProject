package com.example.mapper;

import com.example.domain.Course;
import com.example.domain.Project;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface CourseMapper
{
    @Select("select * from course where id=#{courseId}")
    Course getCourse(Integer courseId);

    @Insert("insert into course(course_code,course_name,credit,hours,teachers,is_team,max_num) values(#{course_code},#{course_name},#{credit},#{hours},#{teachers},#{is_team},#{max_num})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void newCourse(Course course);

    @Update("update course set stu_num=#{num} where id=#{courseId}")
    void updateStuNum(Integer courseId,Integer num);

    @Select("select * from course where teachers=#{teachers}")
    @Results(id="courseMap",value = {
            @Result(id=true, column = "id", property = "id"),
            @Result(property = "projects", column = "id", many = @Many(select = "com.example.mapper.ProjectMapper.queryProjectsByCourse",fetchType = FetchType.EAGER))
    })
    List<Course> getCoursesWithProjects(Integer teachers);
}
