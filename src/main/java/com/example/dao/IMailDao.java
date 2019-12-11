package com.example.dao;

import com.example.domain.Mail;

import java.util.List;

public interface IMailDao
{
    void sendMail(Mail mail);

    Mail getMail(Integer id);

    List<Mail> getMailsByReceiver(Long receiver);

    void deleteMail(Integer id);

    List<Mail> getMailsBySender(Long sender,Integer type);

    void deleteMailBySender(Long sender,Integer type);
}
