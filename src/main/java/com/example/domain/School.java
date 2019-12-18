package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="school")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class School implements Serializable
{
    @Id
    private Integer id;
    private String name;
    @Transient
    private List<Classes> classesList;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Classes> getClassesList()
    {
        return classesList;
    }

    public void setClassesList(List<Classes> classesList)
    {
        this.classesList = classesList;
    }

    @Override
    public String toString()
    {
        return "School{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
