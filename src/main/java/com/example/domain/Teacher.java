package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="teacher")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Teacher implements Serializable
{
    @Id
    private Integer id;
    private String username;
    private String name;

}
