package com.example.service.serviceImpl;

import com.example.dao.IMailDao;
import com.example.domain.Mail;
import com.example.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailServiceImpl implements IMailService
{
    @Autowired
    private IMailDao mailDao;

    @Override
    public void newMail(Long sender, Long receiver)
    {
        mailDao.newMail(sender, receiver);
    }

    @Override
    public Mail getMail(Long sender)
    {
        return mailDao.getMail(sender);
    }

    @Override
    public List<Mail> getMails(Long receiver)
    {
        return mailDao.getMails(receiver);
    }

    @Override
    public void deleteMail(Long sender)
    {
        mailDao.deleteMail(sender);
    }

    @Override
    public boolean existMail(Long sender)
    {
        return mailDao.existMail(sender);
    }
}
