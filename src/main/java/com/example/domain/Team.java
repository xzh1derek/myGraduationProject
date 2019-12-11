package com.example.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="team")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Team implements Serializable
{
    @Id
    private Integer id;
    private Integer course_id;
    private Long leader;
    private Long member1;
    private Long member2;
    private Long member3;
    private Long member4;
    private Long member5;
    private Long member6;
    private Integer current_num;
    private Integer max_num;
    private Boolean is_display;
    private String description;
    private Boolean available;
    private User user;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getCourseId()
    {
        return course_id;
    }

    public void setCourseId(int courseId)
    {
        this.course_id = courseId;
    }

    public Long getLeader()
    {
        return leader;
    }

    public void setLeader(Long leader)
    {
        this.leader = leader;
    }

    public Long getMember1()
    {
        return member1;
    }

    public void setMember1(Long member1)
    {
        this.member1 = member1;
    }

    public Long getMember2()
    {
        return member2;
    }

    public void setMember2(Long member2)
    {
        this.member2 = member2;
    }

    public Long getMember3()
    {
        return member3;
    }

    public void setMember3(Long member3)
    {
        this.member3 = member3;
    }

    public Long getMember4()
    {
        return member4;
    }

    public void setMember4(Long member4)
    {
        this.member4 = member4;
    }

    public Long getMember5()
    {
        return member5;
    }

    public void setMember5(Long member5)
    {
        this.member5 = member5;
    }

    public Long getMember6()
    {
        return member6;
    }

    public void setMember6(Long member6)
    {
        this.member6 = member6;
    }

    public int getCurrentNum()
    {
        return current_num;
    }

    public void setCurrentNum(int currentNum)
    {
        this.current_num = currentNum;
    }

    public int getMaxNum()
    {
        return max_num;
    }

    public void setMaxNum(int maxNum)
    {
        this.max_num = maxNum;
    }

    public boolean isDisplay()
    {
        return is_display;
    }

    public void setDisplay(boolean display)
    {
        is_display = display;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean isAvailable()
    {
        return available;
    }

    public void setAvailable(boolean available)
    {
        this.available = available;
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
        return "Team{" +
                "id=" + id +
                ", courseId=" + course_id +
                ", leader=" + leader +
                ", member1=" + member1 +
                ", member2=" + member2 +
                ", member3=" + member3 +
                ", member4=" + member4 +
                ", member5=" + member5 +
                ", member6=" + member6 +
                ", currentNum=" + current_num +
                ", maxNum=" + max_num +
                ", isDisplay=" + is_display +
                ", description='" + description + '\'' +
                ", available=" + available +
                ", user=" + user +
                '}';
    }
}