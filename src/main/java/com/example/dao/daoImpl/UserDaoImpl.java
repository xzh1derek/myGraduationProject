package com.example.dao.daoImpl;

import com.example.dao.IUserDao;
import com.example.domain.Team;
import com.example.domain.User;
import com.example.mapper.AccountMapper;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Max;
import java.util.List;

@Repository
public class UserDaoImpl implements IUserDao
{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void updateLeader(Long num, boolean b)
    {
        userMapper.updateLeader(num,b);
    }

    @Override
    public void updateTeam(Long num, Long teamleader)
    {
        userMapper.updateTeam(num,teamleader);
    }

    @Override
    public User getUser(Long num)
    {
        return userRepository.getOne(num);
    }

    @Override
    public void updateInvitation(Long num, Long invitationId)
    {
        userMapper.updateInvitation(num,invitationId);
    }

    @Override
    public void updateApplication(Long num, String s)
    {
        userMapper.updateApplication(num,s);
    }

    @Override
    public List<User> findByTeamleader(Long leader)
    {
        return userRepository.findByTeamleader(leader);
    }

    @Override
    public Boolean existUser(Long num)
    {
        return userRepository.existsById(num);
    }

    @Override
    public void saveUser(String username, String password, String name, String school, String qq)
    {
        accountMapper.updatePassword(username,password);
        userMapper.saveUser(Long.parseLong(username),name,school,qq);
    }
}
