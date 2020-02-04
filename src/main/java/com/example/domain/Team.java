package com.example.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
    @Transient
    private Course course;
    @Transient
    private User leaderDetail;
    @Transient
    private List<User> memberDetails;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getCourse_id()
    {
        return course_id;
    }

    public void setCourse_id(Integer course_id)
    {
        this.course_id = course_id;
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

    public Integer getCurrent_num()
    {
        return current_num;
    }

    public void setCurrent_num(Integer current_num)
    {
        this.current_num = current_num;
    }

    public Integer getMax_num()
    {
        return max_num;
    }

    public void setMax_num(Integer max_num)
    {
        this.max_num = max_num;
    }

    public Boolean getIs_display()
    {
        return is_display;
    }

    public void setIs_display(Boolean is_display)
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

    public Boolean getAvailable()
    {
        return available;
    }

    public void setAvailable(Boolean available)
    {
        this.available = available;
    }

    public Course getCourse()
    {
        return course;
    }

    public void setCourse(Course course)
    {
        this.course = course;
    }

    public User getLeaderDetail()
    {
        return leaderDetail;
    }

    public void setLeaderDetail(User leaderDetail)
    {
        this.leaderDetail = leaderDetail;
    }

    public List<User> getMemberDetails()
    {
        return memberDetails;
    }

    public void setMemberDetails(List<User> memberDetails)
    {
        this.memberDetails = memberDetails;
    }

    @Override
    public String toString()
    {
        return "Team{" +
                "id=" + id +
                ", course_id=" + course_id +
                ", leader=" + leader +
                ", member1=" + member1 +
                ", member2=" + member2 +
                ", member3=" + member3 +
                ", member4=" + member4 +
                ", member5=" + member5 +
                ", member6=" + member6 +
                ", current_num=" + current_num +
                ", max_num=" + max_num +
                ", is_display=" + is_display +
                ", description='" + description + '\'' +
                ", available=" + available +
                ", course=" + course +
                ", leaderDetail=" + leaderDetail +
                ", memberDetails=" + memberDetails +
                '}';
    }
}