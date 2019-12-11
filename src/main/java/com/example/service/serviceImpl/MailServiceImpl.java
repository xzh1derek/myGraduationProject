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
    public void sendMail(Mail mail)
    {
        mailDao.sendMail(mail);
    }

    @Override
    public Mail getMail(Integer id)
    {
        return mailDao.getMail(id);
    }

    @Override
    public List<Mail> getMailsByReceiver(Long receiver)
    {
        return mailDao.getMailsByReceiver(receiver);
    }

    @Override
    public void deleteMail(Integer id)
    {
        mailDao.deleteMail(id);
    }

    @Override
    public List<Mail> myApplication(Long username)
    {
        return mailDao.getMailsBySender(username,2);
    }

    @Override
    public boolean hasApplied(Long username)
    {
        List<Mail> mailList = mailDao.getMailsBySender(username,2);
        return !mailList.isEmpty();
    }

    @Override
    public void deleteMailBySender(Long sender,Integer type)
    {
        mailDao.deleteMailBySender(sender,type);
    }
}
