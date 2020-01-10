package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class User implements Serializable
{
    @Id
    private Long username;
    private String name;
    private String school;
    private Integer class_id;
    private String qq;
    private String year;
    private Integer new_message;
    @Transient//属于实体的临时数据，jpa不写在数据库字段里
    private List<UserCourse> userCourses;

    public Long getUsername()
    {
        return username;
    }

    public void setUsername(Long username)
    {
        this.username = username;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getClass_id()
    {
        return class_id;
    }

    public void setClass_id(Integer classId)
    {
        this.class_id = classId;
    }

    public String getSchool()
    {
        return school;
    }

    public void setSchool(String school)
    {
        this.school = school;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public String getQq()
    {
        return qq;
    }

    public void setQq(String qq)
    {
        this.qq = qq;
    }

    public Integer getNew_message()
    {
        return new_message;
    }

    public void setNew_message(Integer new_message)
    {
        this.new_message = new_message;
    }

    public List<UserCourse> getUserCourses()
    {
        return userCourses;
    }

    public void setUserCourses(List<UserCourse> userCourses)
    {
        this.userCourses = userCourses;
    }

    @Override
    public String toString()
    {
        return "User{" +
                ", username=" + username +
                ", name='" + name + '\'' +
                ", school='" + school + '\'' +
                ", class_id=" + class_id +
                ", qq='" + qq + '\'' +
                ", year='" + year + '\'' +
                ", new_message=" + new_message +
                ", userCourses=" + userCourses +
                '}';
    }
}
