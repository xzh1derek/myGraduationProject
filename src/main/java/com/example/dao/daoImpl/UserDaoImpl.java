package com.example.dao.daoImpl;

import com.example.dao.IUserDao;
import com.example.domain.Team;
import com.example.domain.User;
import com.example.domain.UserCourse;
import com.example.mapper.AccountMapper;
import com.example.mapper.UserCourseMapper;
import com.example.mapper.UserMapper;
import com.example.repository.UserCourseRepository;
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
    private UserCourseMapper userCourseMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void updateLeader(Long num, boolean b,Integer courseId){userCourseMapper.updateLeader(num,courseId,b);}

    @Override
    public void updateTeamId(Long num, Integer teamId, Integer courseId)
    {
        userCourseMapper.updateTeamId(num,teamId,courseId);
    }

    @Override
    public User getUser(Long num)
    {
        return userMapper.getUser(num);
    }

    @Override
    public User findAUser(Long num)
    {
        return userMapper.findAUser(num);
    }

    @Override
    public Boolean existUser(Long num)
    {
        return userRepository.existsById(num);
    }

    @Override
    public UserCourse getUserCourse(Long u,Integer c)
    {
        return userCourseMapper.getUserCourse(u,c);
    }
}
