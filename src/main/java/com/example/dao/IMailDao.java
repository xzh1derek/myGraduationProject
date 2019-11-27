package com.example.dao;

import com.example.domain.Mail;

import java.util.List;

public interface IMailDao
{
    void newMail(Long sender,Long receiver);

    Mail getMail(Long sender);

    List<Mail> getMails(Long receiver);

    void deleteMail(Long sender);

    boolean existMail(Long sender);
}
