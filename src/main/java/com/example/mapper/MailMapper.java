package com.example.mapper;

import com.example.domain.Mail;
import com.example.domain.Team;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface MailMapper
{
    @Select("select * from mail where receiver=#{receiver}")
    List<Mail> getMailsByReceiver(Long receiver);

    @Select("select * from mail where sender=#{sender} and type=#{type}")
    List<Mail> getMailsBySender(Long sender,Integer type);

    @Insert("insert into mail(sender,receiver,type,team_id,text) values(#{sender},#{receiver},#{type},#{team_id},#{text})")
    void sendMail(Mail mail);

    @Select("select * from mail where id=#{id}")
    Mail getMail(Integer id);

    @Delete("delete from mail where id=#{id}")
    void deleteMail(Integer id);

    @Delete("delete from mail where sender=#{sender} and type =#{type}")
    void deleteMails(Long sender,Integer type);

    @Delete("delete from mail")
    void deleteAllMails();
}
