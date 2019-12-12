package com.example.mapper;

import com.example.domain.Course;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CourseMapper
{
    @Select("select * from course where id=#{courseId}")
    Course getCourse(Integer courseId);

    @Insert("insert into course(course_code,course_name,credit,hours,teachers,is_team,max_num) values(#{course_code},#{course_name},#{credit},#{hours},#{teachers},#{is_team},#{max_num})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void newCourse(Course course);

    @Update("update course set stu_num=#{num} where course_id=#{courseId}")
    void updateStuNum(Integer courseId,Integer num);

}
