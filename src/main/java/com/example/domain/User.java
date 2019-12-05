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
    @JsonIgnore
    private int id;
    @Id
    private Long username;
    private String name;
    private String school;
    private String qq;
    private List<UserCourse> userCourses;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

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

    public String getSchool()
    {
        return school;
    }

    public void setSchool(String school)
    {
        this.school = school;
    }

    public String getQq()
    {
        return qq;
    }

    public void setQq(String qq)
    {
        this.qq = qq;
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
                "id=" + id +
                ", username=" + username +
                ", name='" + name + '\'' +
                ", school='" + school + '\'' +
                ", qq='" + qq + '\'' +
                ", userCourses=" + userCourses +
                '}';
    }
}
