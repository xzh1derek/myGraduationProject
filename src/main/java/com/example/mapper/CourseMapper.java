package com.example.mapper;

import com.example.domain.Course;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface CourseMapper
{
    @Select("select * from course where id=#{courseId}")
    Course getCourse(Integer courseId);

    @Insert("insert into course(course_code,course_name,credit,hours,teacher,is_team,max_num) values(#{course_code},#{course_name},#{credit},#{hours},#{teacher},#{is_team},#{max_num})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void newCourse(Course course);

    @Update("update course set course_code=#{course_code},course_name=#{course_name},credit=#{credit},hours=#{hours},teacher=#{teacher},is_team=#{is_team},max_num=#{max_num} where id=#{id}")
    void updateCourse(Course course);

    @Update("update course set stu_num=#{num} where id=#{courseId}")
    void updateStuNum(Integer courseId,Integer num);

    @Select("select * from course")
    @Results(id="courseMapWithProjects",value = {
            @Result(id=true, column = "id", property = "id"),
            @Result(property = "projects", column = "id", many = @Many(select = "com.example.mapper.ProjectMapper.queryProjectsByCourse",fetchType = FetchType.LAZY))
    })
    List<Course> queryCourseWithProjects();

    @Insert("insert into class_course(class_id,course_id,school_id) values(#{class_id},#{course_id},#{school_id})")
    void newClassCourse(Integer class_id, Integer course_id,Integer school_id);

    @Select("select * from course")
    @Results(id="courseMapWithClasses",value={
            @Result(id = true,column = "id",property = "id"),
            @Result(property = "classesList",column = "id",many = @Many(select = "com.example.mapper.SchoolAndClassMapper.queryClassByCourse",fetchType = FetchType.LAZY))
    })
    List<Course> queryCourseWithClasses();

    @Delete("delete from class_course where course_id=#{courseId}")
    void deleteClassCourse(Integer courseId);
}
