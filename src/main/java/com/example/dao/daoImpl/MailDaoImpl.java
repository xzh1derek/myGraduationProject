package com.example.dao.daoImpl;

import com.example.dao.IMailDao;
import com.example.domain.Mail;
import com.example.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MailDaoImpl implements IMailDao
{
    @Autowired
    private MailRepository mailRepository;

    @Override
    public void newMail(Long sender, Long receiver)
    {
        Mail mail = new Mail();
        mail.setSender(sender);
        mail.setReceiver(receiver);
        mailRepository.save(mail);
    }

    @Override
    public Mail getMail(Long sender)
    {
        return mailRepository.getOne(sender);
    }

    @Override
    public List<Mail> getMails(Long receiver)
    {
        return mailRepository.findByReceiver(receiver);
    }

    @Override
    public void deleteMail(Long sender)
    {
        mailRepository.deleteById(sender);
    }

    @Override
    public boolean existMail(Long sender)
    {
        return mailRepository.existsById(sender);
    }
}
