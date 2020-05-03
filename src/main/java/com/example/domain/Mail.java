package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

public class Mail implements Serializable
{
    private int id;
    private Long sender;
    private String receiver;
    private Integer type;//0为系统通知，1为邀请函，2为申请信
    private Integer team_id;
    private String text;

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

    public String getReceiver()
    {
        return receiver;
    }

    public void setReceiver(String receiver)
    {
        this.receiver = receiver;
    }

    public Integer getTeam_id()
    {
        return team_id;
    }

    public void setTeam_id(Integer team_id)
    {
        this.team_id = team_id;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getTeamId()
    {
        return team_id;
    }

    public void setTeamId(Integer teamId)
    {
        this.team_id = teamId;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return "Mail{" +
                "id=" + id +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", type=" + type +
                ", team_id=" + team_id +
                ", text='" + text + '\'' +
                '}';
    }
}
