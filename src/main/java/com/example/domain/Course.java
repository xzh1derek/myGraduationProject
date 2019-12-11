package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="course")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Course implements Serializable
{
    @Id
    private Integer id;
    private String course_code;
    private String course_name;
    private Float credit;
    private Integer hours;
    private Integer teachers;
    private Boolean is_team;
    private Integer max_num;
    private Date create_time;
    private Integer stu_num;
    private Integer project_num;
    private String template;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getCourse_code()
    {
        return course_code;
    }

    public void setCourse_code(String course_code)
    {
        this.course_code = course_code;
    }

    public String getCourse_name()
    {
        return course_name;
    }

    public void setCourse_name(String course_name)
    {
        this.course_name = course_name;
    }

    public Float getCredit()
    {
        return credit;
    }

    public void setCredit(Float credit)
    {
        this.credit = credit;
    }

    public Integer getHours()
    {
        return hours;
    }

    public void setHours(Integer hours)
    {
        this.hours = hours;
    }

    public Integer getTeachers()
    {
        return teachers;
    }

    public void setTeachers(Integer teachers)
    {
        this.teachers = teachers;
    }

    public Boolean getIs_team()
    {
        return is_team;
    }

    public void setIs_team(Boolean is_team)
    {
        this.is_team = is_team;
    }

    public Integer getMax_num()
    {
        return max_num;
    }

    public void setMax_num(Integer max_num)
    {
        this.max_num = max_num;
    }

    public Date getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(Date create_time)
    {
        this.create_time = create_time;
    }

    public Integer getStu_num()
    {
        return stu_num;
    }

    public void setStu_num(Integer stu_num)
    {
        this.stu_num = stu_num;
    }

    public Integer getProject_num()
    {
        return project_num;
    }

    public void setProject_num(Integer project_num)
    {
        this.project_num = project_num;
    }

    public String getTemplate()
    {
        return template;
    }

    public void setTemplate(String template)
    {
        this.template = template;
    }

    @Override
    public String toString()
    {
        return "Course{" +
                "id=" + id +
                ", course_code='" + course_code + '\'' +
                ", course_name='" + course_name + '\'' +
                ", credit=" + credit +
                ", hours=" + hours +
                ", teachers=" + teachers +
                ", is_team=" + is_team +
                ", max_num=" + max_num +
                ", create_time=" + create_time +
                ", stu_num=" + stu_num +
                ", project_num=" + project_num +
                ", template='" + template + '\'' +
                '}';
    }
}
