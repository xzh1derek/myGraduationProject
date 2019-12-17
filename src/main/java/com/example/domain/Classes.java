package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="classes")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Classes implements Serializable
{
    @Id
    private Integer class_id;
    private Integer school_id;
    private School school;

    public Integer getClass_id()
    {
        return class_id;
    }

    public void setClass_id(Integer class_id)
    {
        this.class_id = class_id;
    }

    public Integer getSchool_id()
    {
        return school_id;
    }

    public void setSchool_id(Integer school_id)
    {
        this.school_id = school_id;
    }

    public School getSchool()
    {
        return school;
    }

    public void setSchool(School school)
    {
        this.school = school;
    }

    @Override
    public String toString()
    {
        return "Classes{" +
                "class_id=" + class_id +
                ", school_id=" + school_id +
                ", school=" + school +
                '}';
    }
}
