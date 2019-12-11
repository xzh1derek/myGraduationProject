package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "module")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Module implements Serializable
{
    @Id
    private Integer id;
    private Integer project_id;
    private String module_name;
    private String time;
    private String location;
    private Integer stu_num;
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

    public String getModule_name()
    {
        return module_name;
    }

    public void setModule_name(String module_name)
    {
        this.module_name = module_name;
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
}
