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
    @Transient
    private User user;

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

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "UserModule{" +
                "id=" + id +
                ", username=" + username +
                ", module_id=" + module_id +
                ", user=" + user +
                '}';
    }
}
