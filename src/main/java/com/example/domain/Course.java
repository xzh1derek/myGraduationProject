package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
    private Integer hours;
    @JsonIgnore
    private Long teacher;
    private Boolean is_team;
    private Integer max_num;
    private Integer stu_num;
    private String template;
    private Boolean is_published;
    @Transient
    private List<Project> projects;
    @Transient
    private List<Teacher> teachers;

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

    public Integer getHours()
    {
        return hours;
    }

    public void setHours(Integer hours)
    {
        this.hours = hours;
    }

    public Long getTeacher()
    {
        return teacher;
    }

    public void setTeacher(Long teacher)
    {
        this.teacher = teacher;
    }

    public Boolean getIs_team()
    {
        return is_team;
    }

    public void setIs_team(Boolean is_team)
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

    public Integer getStu_num()
    {
        return stu_num;
    }

    public void setStu_num(Integer stu_num)
    {
        this.stu_num = stu_num;
    }

    public String getTemplate()
    {
        return template;
    }

    public void setTemplate(String template)
    {
        this.template = template;
    }

    public Boolean getIs_published()
    {
        return is_published;
    }

    public void setIs_published(Boolean is_published)
    {
        this.is_published = is_published;
    }

    public List<Project> getProjects()
    {
        return projects;
    }

    public void setProjects(List<Project> projects)
    {
        this.projects = projects;
    }

    public List<Teacher> getTeachers()
    {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers)
    {
        this.teachers = teachers;
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
                ", teacher=" + teacher +
                ", is_team=" + is_team +
                ", max_num=" + max_num +
                ", stu_num=" + stu_num +
                ", template='" + template + '\'' +
                ", projects=" + projects +
                ", teachers=" + teachers +
                '}';
    }
}
