package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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

    public Integer getModuleId()
    {
        return module_id;
    }

    public void setModuleId(Integer moduleId)
    {
        this.module_id = moduleId;
    }

    @Override
    public String toString()
    {
        return "UserModule{" +
                "id=" + id +
                ", username=" + username +
                ", moduleId=" + module_id +
                '}';
    }
}
