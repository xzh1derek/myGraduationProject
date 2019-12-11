package com.example.dao.daoImpl;

import com.example.dao.IMailDao;
import com.example.domain.Mail;
import com.example.mapper.MailMapper;
import com.example.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MailDaoImpl implements IMailDao
{
    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private MailMapper mailMapper;

    @Override
    public void sendMail(Mail mail)
    {
        mailRepository.save(mail);
    }

    @Override
    public Mail getMail(Integer id)
    {
        return mailRepository.getOne(id);
    }

    @Override
    public List<Mail> getMailsByReceiver(Long receiver)
    {
        return mailMapper.getMailsByReceiver(receiver);
    }

    @Override
    public void deleteMail(Integer id)
    {
        mailRepository.deleteById(id);
    }

    @Override
    public List<Mail> getMailsBySender(Long sender, Integer type)
    {
        return mailMapper.getMailsBySender(sender,type);
    }

    @Override
    public void deleteMailBySender(Long sender,Integer type)
    {
        mailMapper.deleteMail(sender,type);
    }
}
