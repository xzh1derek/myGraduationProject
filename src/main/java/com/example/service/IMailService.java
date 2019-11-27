package com.example.service;

import com.example.domain.Mail;

import java.util.List;

public interface IMailService
{
    void newMail(Long sender,Long receiver);

    Mail getMail(Long sender);

    List<Mail> getMails(Long receiver);

    void deleteMail(Long sender);

    boolean existMail(Long sender);
}
