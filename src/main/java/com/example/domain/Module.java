package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
@Table(name = "module")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Module implements Serializable
{
    @Id
    private Integer id;
    private Integer project_id;
    private Integer module_index;
    private String time;
    private String location;
    private Integer stu_num;
    @Transient
    private Project project;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getProject_id()
    {
        return project_id;
    }

    public void setProject_id(Integer project_id)
    {
        this.project_id = project_id;
    }

    public Integer getModule_index()
    {
        return module_index;
    }

    public void setModule_index(Integer module_index)
    {
        this.module_index = module_index;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public Integer getStu_num()
    {
        return stu_num;
    }

    public void setStu_num(Integer stu_num)
    {
        this.stu_num = stu_num;
    }

    public Project getProject()
    {
        return project;
    }

    public void setProject(Project project)
    {
        this.project = project;
    }

    @Override
    public String toString()
    {
        return "Module{" +
                "id=" + id +
                ", project_id=" + project_id +
                ", module_index=" + module_index +
                ", time='" + time + '\'' +
                ", location='" + location + '\'' +
                ", stu_num=" + stu_num +
                ", project=" + project +
                '}';
    }
}
