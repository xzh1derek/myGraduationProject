package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "project")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Project implements Serializable
{
    @Id
    private Integer id;
    private String project_name;
    private Integer project_index;
    private Integer course_id;
    private Integer hours;
    private Integer teacher;
    private Boolean is_fixed;//true为固定，false为任选
    @Transient
    private Course course;
    @Transient
    private List<Module> modules;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getProject_name()
    {
        return project_name;
    }

    public void setProject_name(String project_name)
    {
        this.project_name = project_name;
    }

    public Integer getProject_index()
    {
        return project_index;
    }

    public void setProject_index(Integer project_index)
    {
        this.project_index = project_index;
    }

    public Integer getCourse_id()
    {
        return course_id;
    }

    public void setCourse_id(Integer course_id)
    {
        this.course_id = course_id;
    }

    public Integer getHours()
    {
        return hours;
    }

    public void setHours(Integer hours)
    {
        this.hours = hours;
    }

    public Integer getTeacher()
    {
        return teacher;
    }

    public void setTeacher(Integer teacher)
    {
        this.teacher = teacher;
    }

    public Boolean getIs_fixed()
    {
        return is_fixed;
    }

    public void setIs_fixed(Boolean is_fixed)
    {
        this.is_fixed = is_fixed;
    }

    public Course getCourse()
    {
        return course;
    }

    public void setCourse(Course course)
    {
        this.course = course;
    }

    public List<Module> getModules()
    {
        return modules;
    }

    public void setModules(List<Module> modules)
    {
        this.modules = modules;
    }

    @Override
    public String toString()
    {
        return "Project{" +
                "id=" + id +
                ", project_name='" + project_name + '\'' +
                ", project_index=" + project_index +
                ", course_id=" + course_id +
                ", hours=" + hours +
                ", is_fixed=" + is_fixed +
                ", course=" + course +
                ", modules=" + modules +
                '}';
    }
}
