package com.example.mapper;

import com.example.domain.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CourseMapper
{
    @Select("select * from course where id=#{courseId}")
    Course getCourse(Integer courseId);
}
