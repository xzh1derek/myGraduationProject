package com.example.mapper;

import com.example.domain.Classes;
import com.example.domain.School;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface SchoolAndClassMapper
{
    @Insert("insert into school(name) values(#{name})")
    void newSchool(String name);

    @Delete("delete from school where id=#{id}")
    void deleteSchool(Integer id);

    @Update("update school set name=#{name} where id=#{id}")
    void updateSchool(Integer id,String name);

    @Select("select * from school")
    List<School> querySchool();

    @Select("select * from school where id=#{id}")
    School GetSchool(Integer id);

    @Insert("insert into classes(class_id,school_id) values(#{class_id},#{school_id})")
    void newClass(Integer class_id, Integer school_id);

    @Delete("delete from classes where class_id=#{id}")
    void deleteClass(Integer id);

    @Select("select * from classes")
    @Results(id="classMap",value = {
            @Result(column = "school_id", property = "school_id"),
            @Result(property = "school", column = "school_id", one = @One(select = "com.example.mapper.OthersMapper.GetSchool",fetchType = FetchType.EAGER))
    })
    List<Classes> queryAllClasses();

    @Select("select * from school")
    @Results(id="schoolMapWithClasses",value = {
            @Result(id=true, column = "id", property = "id"),
            @Result(property = "classesList", column = "id", many = @Many(select = "com.example.mapper.SchoolAndClassMapper.queryClassesBySchool",fetchType = FetchType.LAZY))
    })
    List<School> querySchoolWithClasses();

//    @Select("select * from school")
//    @Results(id="schoolMapWithLimitedClasses",value = {
//            @Result(id=true, column = "id", property = "id"),
//            @Result(property = "classesList", column = "id", many = @Many(select = "com.example.mapper.SchoolAndClassMapper.queryClassesBySchoolAndCourse",fetchType = FetchType.LAZY))
//    })
//    List<School> querySchoolWithClassesLimited(Integer course_id);

    @Select("select class_id from classes where school_id=#{school_id}")
    List<Integer> queryClassesBySchool(Integer school_id);

    @Select("select class_id from class_course where course_id=#{course_id} and school_id=#{school_id}")
    List<Integer> queryClassBySchoolAndCourse(Integer school_id, Integer course_id);
}
