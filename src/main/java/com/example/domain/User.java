package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class User implements Serializable
{
    @JsonIgnore
    private int id;
    @Id
    private Long username;
    private String name;
    private String school;
    private String qq;
    private boolean isLeader;
    private Long teamleader;
    private String applicationStatus;
    private Long invitation_id;

    //public User(){}

    public int getId()
    {
        return id;
    }

    public void setId(int id)
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSchool()
    {
        return school;
    }

    public void setSchool(String school)
    {
        this.school = school;
    }

    public String getQq()
    {
        return qq;
    }

    public void setQq(String qq)
    {
        this.qq = qq;
    }

    public boolean isLeader()
    {
        return isLeader;
    }

    public void setLeader(boolean leader)
    {
        isLeader = leader;
    }

    public Long getTeamleader()
    {
        return teamleader;
    }

    public void setTeamleader(Long teamleader)
    {
        this.teamleader = teamleader;
    }

    public String getApplicationStatus()
    {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus)
    {
        this.applicationStatus = applicationStatus;
    }

    public Long getInvitation_id()
    {
        return invitation_id;
    }

    public void setInvitation_id(Long invitation_id)
    {
        this.invitation_id = invitation_id;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", school='" + school + '\'' +
                ", qq='" + qq + '\'' +
                ", isLeader=" + isLeader +
                ", team_id=" + teamleader +
                ", applicationStatus='" + applicationStatus + '\'' +
                ", invitation_id=" + invitation_id +
                '}';
    }
}
