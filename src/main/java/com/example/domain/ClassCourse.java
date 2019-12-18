package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="class_course")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class ClassCourse implements Serializable
{
    @Id
    private Integer id;
    private Integer class_id;
    private Integer course_id;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getClass_id()
    {
        return class_id;
    }

    public void setClass_id(Integer class_id)
    {
        this.class_id = class_id;
    }

    public Integer getCourse_id()
    {
        return course_id;
    }

    public void setCourse_id(Integer course_id)
    {
        this.course_id = course_id;
    }

    @Override
    public String toString()
    {
        return "ClassCourse{" +
                "id=" + id +
                ", class_id=" + class_id +
                ", course_id=" + course_id +
                '}';
    }
}
