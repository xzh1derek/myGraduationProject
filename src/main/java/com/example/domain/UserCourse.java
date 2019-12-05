package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="user_course")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class UserCourse implements Serializable
{
    @Id
    private Integer id;
    private Long username;
    private Integer courseId;
    private Boolean courseIsTeam;
    private Boolean isLeader;
    private Long teamleader;
    private String applicationStatus;
    private Long invitationId;

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

    public Integer getCourseId()
    {
        return courseId;
    }

    public void setCourseId(Integer courseId)
    {
        this.courseId = courseId;
    }

    public Boolean getTeam()
    {
        return courseIsTeam;
    }

    public void setTeam(Boolean team)
    {
        courseIsTeam = team;
    }

    public Boolean getLeader()
    {
        return isLeader;
    }

    public void setLeader(Boolean leader)
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

    public Long getInvitationId()
    {
        return invitationId;
    }

    public void setInvitationId(Long invitationId)
    {
        this.invitationId = invitationId;
    }

    @Override
    public String toString()
    {
        return "UserCourse{" +
                "id=" + id +
                ", username=" + username +
                ", courseId=" + courseId +
                ", isTeam=" + courseIsTeam +
                ", isLeader=" + isLeader +
                ", teamleader=" + teamleader +
                ", applicationStatus='" + applicationStatus + '\'' +
                ", invitationId=" + invitationId +
                '}';
    }
}
