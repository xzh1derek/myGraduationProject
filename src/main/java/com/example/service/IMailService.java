package com.example.service;

import com.example.domain.Mail;

import java.util.List;

public interface IMailService
{
    void sendMail(Long sender,Long receiver,Integer type,Integer teamId,String text);

    Mail getMail(Integer id);

    List<Mail> getMailsByReceiver(Long receiver);

    void deleteMail(Integer id);

    List<Mail> myApplication(Long username);

    boolean hasApplied(Long username);

    void deleteMailBySender(Long sender,Integer type);
}
