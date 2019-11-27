package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="team")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Team implements Serializable
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Id
    private Long leader;
    private Long member1;
    private Long member2;
    private Long member3;
    private Long member4;
    private int current_num;
    private int max_num;
    private boolean is_display;
    private String description;
    private boolean available;
    private User user;

    //public Team(){}

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
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

    public int getCurrent_num()
    {
        return current_num;
    }

    public void setCurrent_num(int current_num)
    {
        this.current_num = current_num;
    }

    public int getMax_num()
    {
        return max_num;
    }

    public void setMax_num(int max_num)
    {
        this.max_num = max_num;
    }

    public boolean isIs_display()
    {
        return is_display;
    }

    public void setIs_display(boolean is_display)
    {
        this.is_display = is_display;
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

    @Override
    public String toString()
    {
        return "Team{" +
                "id=" + id +
                ", leader=" + leader +
                ", member1=" + member1 +
                ", member2=" + member2 +
                ", member3=" + member3 +
                ", member4=" + member4 +
                ", current_num=" + current_num +
                ", max_num=" + max_num +
                ", is_display=" + is_display +
                ", description='" + description + '\'' +
                ", available=" + available +
                ", user=" + user +
                '}';
    }
}