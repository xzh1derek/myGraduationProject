package com.example.service.serviceImpl;

import com.example.dao.IMailDao;
import com.example.dao.IUserDao;
import com.example.domain.Mail;
import com.example.domain.User;
import com.example.service.IMailService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailServiceImpl implements IMailService
{
    @Autowired
    private IMailDao mailDao;
    @Autowired
    private IUserDao userDao;

    @Override
    public void sendMail(Long sender,Long receiver,Integer type,Integer teamId,String text)
    {
        Mail mail = new Mail();
        mail.setSender(sender);
        mail.setReceiver(receiver);
        mail.setType(type);
        mail.setTeamId(teamId);
        mail.setText(text);
        mailDao.sendMail(mail);
        User user = userDao.findAUser(receiver);
        user.setNew_message(user.getNew_message()+1);
        userDao.saveUser(user);
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
