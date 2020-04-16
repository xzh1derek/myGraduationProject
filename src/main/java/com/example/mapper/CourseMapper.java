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

    @Insert("insert into course(course_code,course_name,credit,hours,is_team,max_num,is_published) values(#{course_code},#{course_name},#{credit},#{hours},#{is_team},#{max_num},0)")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void newCourse(Course course);

    @Update("update course set course_code=#{course_code},course_name=#{course_name},credit=#{credit},hours=#{hours},is_team=#{is_team},max_num=#{max_num} where id=#{id}")
    void updateCourse(Course course);

    @Update("update course set teacher=#{teacher} where id=#{courseId}")
    void updateTeachers(Integer courseId,Long teacher);

    @Update("update course set stu_num=#{num} where id=#{courseId}")
    void updateStuNum(Integer courseId,Integer num);

    @Update("update course set is_published=#{status} where id=#{id}")
    void updateIsPublished(Integer id,Boolean status);

    @Select("select * from course")
    @Results(id="courseMapWithProjects",value = {
            @Result(id=true, column = "id", property = "id"),
            @Result(property = "projects", column = "id", many = @Many(select = "com.example.mapper.ProjectMapper.queryProjectsByCourse",fetchType = FetchType.LAZY))
    })
    List<Course> queryCourseWithProjects();

    @Insert("insert into class_course(class_id,course_id,school_id) values(#{class_id},#{course_id},#{school_id})")
    void newClassCourse(Integer class_id, Integer course_id,Integer school_id);

    @Delete("delete from class_course where course_id=#{courseId}")
    void deleteClassCourse(Integer courseId);

    @Delete("delete from team where course_id=#{courseId]")
    void deleteTeamsByCourse(Integer courseId);

    @Select("select * from course where teacher&#{teacher}=#{teacher} and is_published=true")
    List<Course> queryCourseByTeacher(Long teacher);

    @Select("select * from course where is_team=#{status}")
    List<Course> queryCourseIsTeam(Boolean status);
}
