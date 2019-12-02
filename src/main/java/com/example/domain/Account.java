package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "account")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Account implements Serializable
{
    @JsonIgnore
    private Integer id;
    @Id
    private String username;
    private String password;
    private Integer identity;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Integer getIdentity()
    {
        return identity;
    }

    public void setIdentity(Integer identity)
    {
        this.identity = identity;
    }

    @Override
    public String toString()
    {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", identity=" + identity +
                '}';
    }
}
