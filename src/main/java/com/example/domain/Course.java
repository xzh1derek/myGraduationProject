package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="course")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Course implements Serializable
{
    @Id
    private Integer id;
    private String course_code;
    private String course_name;
    private Float credit;
    private Float hours;
    private String teachers;
    private Boolean is_team;
    private Integer max_num;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getCourse_code()
    {
        return course_code;
    }

    public void setCourse_code(String course_code)
    {
        this.course_code = course_code;
    }

    public String getCourse_name()
    {
        return course_name;
    }

    public void setCourse_name(String course_name)
    {
        this.course_name = course_name;
    }

    public Float getCredit()
    {
        return credit;
    }

    public void setCredit(Float credit)
    {
        this.credit = credit;
    }

    public Float getHours()
    {
        return hours;
    }

    public void setHours(Float hours)
    {
        this.hours = hours;
    }

    public String getTeachers()
    {
        return teachers;
    }

    public void setTeachers(String teachers)
    {
        this.teachers = teachers;
    }

    public boolean isIs_team()
    {
        return is_team;
    }

    public void setIs_team(boolean is_team)
    {
        this.is_team = is_team;
    }

    public Integer getMax_num()
    {
        return max_num;
    }

    public void setMax_num(Integer max_num)
    {
        this.max_num = max_num;
    }

    @Override
    public String toString()
    {
        return "Course{" +
                "id=" + id +
                ", course_code='" + course_code + '\'' +
                ", course_name='" + course_name + '\'' +
                ", credit=" + credit +
                ", hours=" + hours +
                ", teachers='" + teachers + '\'' +
                ", is_team=" + is_team +
                ", max_num=" + max_num +
                '}';
    }
}
