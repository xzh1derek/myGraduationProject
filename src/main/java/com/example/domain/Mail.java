package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "mail")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Mail implements Serializable
{
    @JsonIgnore
    private int id;
    @Id
    private Long sender;
    private Long receiver;
    private User user;

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

    public Long getSender()
    {
        return sender;
    }

    public void setSender(Long sender)
    {
        this.sender = sender;
    }

    public Long getReceiver()
    {
        return receiver;
    }

    public void setReceiver(Long receiver)
    {
        this.receiver = receiver;
    }

    @Override
    public String toString()
    {
        return "Mail{" +
                "id=" + id +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", user=" + user +
                '}';
    }
}
