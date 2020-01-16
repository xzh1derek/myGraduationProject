package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
@Table(name="user_module")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class UserModule implements Serializable
{
    @Id
    private Integer id;
    private Long username;
    private Integer module_id;
    @JsonIgnore
    private Long teacher;
    private Boolean is_team;
    private Boolean is_fixed;
    @Transient
    private User user;
    @Transient
    private Module module;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
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

    public Integer getModule_id()
    {
        return module_id;
    }

    public void setModule_id(Integer module_id)
    {
        this.module_id = module_id;
    }

    public Module getModule()
    {
        return module;
    }

    public void setModule(Module module)
    {
        this.module = module;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
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

    public Boolean getIs_fixed()
    {
        return is_fixed;
    }

    public void setIs_fixed(Boolean is_fixed)
    {
        this.is_fixed = is_fixed;
    }

    @Override
    public String toString()
    {
        return "UserModule{" +
                "id=" + id +
                ", username=" + username +
                ", module_id=" + module_id +
                ", teacher=" + teacher +
                ", is_team=" + is_team +
                ", is_fixed=" + is_fixed +
                ", user=" + user +
                ", module=" + module +
                '}';
    }
}
