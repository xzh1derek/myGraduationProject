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
    private Integer course_id;
    private Boolean is_leader;
    private Integer team_id;
    private Float score;
    private String file;
    private Course course;

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

    public Integer getCourse_id()
    {
        return course_id;
    }

    public void setCourse_id(Integer course_id)
    {
        this.course_id = course_id;
    }

    public Boolean getIs_leader()
    {
        return is_leader;
    }

    public void setIs_leader(Boolean is_leader)
    {
        this.is_leader = is_leader;
    }

    public Integer getTeam_id()
    {
        return team_id;
    }

    public void setTeam_id(Integer team_id)
    {
        this.team_id = team_id;
    }

    public Float getScore()
    {
        return score;
    }

    public void setScore(Float grade)
    {
        this.score = grade;
    }

    public String getFile()
    {
        return file;
    }

    public void setFile(String file)
    {
        this.file = file;
    }

    public Course getCourse()
    {
        return course;
    }

    public void setCourse(Course course)
    {
        this.course = course;
    }

    @Override
    public String toString()
    {
        return "UserCourse{" +
                "id=" + id +
                ", username=" + username +
                ", course_id=" + course_id +
                ", is_leader=" + is_leader +
                ", team_id=" + team_id +
                ", score=" + score +
                ", file='" + file + '\'' +
                ", course=" + course +
                '}';
    }
}
