package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "module")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Module implements Serializable
{
    @Id
    private Integer id;
    private Integer project_id;
    private Integer module_index;
    private Date date;//数据库里存的Date
    private String time;
    private String location;
    private Integer stu_num;
    private Integer class1;
    private Integer class2;
    private String dateOfString;//MVC传来的date
    @Transient
    private Project project;
    @Transient
    private List<UserModule> userModules;

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

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getDateOfString()
    {
        return dateOfString;
    }

    public void setDateOfString(String dateOfString)
    {
        this.dateOfString = dateOfString;
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

    public List<UserModule> getUserModules()
    {
        return userModules;
    }

    public void setUserModules(List<UserModule> userModules)
    {
        this.userModules = userModules;
    }

    public Integer getClass1()
    {
        return class1;
    }

    public void setClass1(Integer class1)
    {
        this.class1 = class1;
    }

    public Integer getClass2()
    {
        return class2;
    }

    public void setClass2(Integer class2)
    {
        this.class2 = class2;
    }

    @Override
    public String toString()
    {
        return "Module{" +
                "id=" + id +
                ", project_id=" + project_id +
                ", module_index=" + module_index +
                ", Date=" + date +
                ", time='" + time + '\'' +
                ", location='" + location + '\'' +
                ", stu_num=" + stu_num +
                ", class1=" + class1 +
                ", class2=" + class2 +
                ", project=" + project +
                ", userModules=" + userModules +
                '}';
    }
}
