package com.example.service.serviceImpl;

import com.example.dao.IUserDao;
import com.example.domain.User;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService
{
    @Autowired
    private IUserDao userDao;

    @Override
    public void updateLeader(Long num, boolean b)
    {
        userDao.updateLeader(num,b);
    }

    @Override
    public void updateTeam(Long num, Long teamleader)
    {
        userDao.updateTeam(num,teamleader);
    }

    @Override
    public User getUser(Long num)
    {
        return userDao.getUser(num);
    }

    @Override
    public void updateInvitation(Long num, Long invitationId)
    {
        userDao.updateInvitation(num,invitationId);
    }

    @Override
    public void updateApplication(Long num, String s)
    {
        userDao.updateApplication(num,s);
    }

    @Override
    public List<User> findByTeamleader(Long leader)
    {
        return userDao.findByTeamleader(leader);
    }

    @Override
    public Boolean existUser(Long num)
    {
        return userDao.existUser(num);
    }
}
