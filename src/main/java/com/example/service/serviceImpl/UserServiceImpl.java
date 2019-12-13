package com.example.service.serviceImpl;

import com.example.dao.IUserDao;
import com.example.domain.User;
import com.example.domain.UserCourse;
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
    public void updateLeader(UserCourse userCourse)
    {
        userCourse.setIs_leader(true);
        userDao.saveUserCourse(userCourse);
    }

    @Override
    public void updateTeamId(UserCourse userCourse,Integer teamId)
    {
        userCourse.setTeam_id(teamId);
        userDao.saveUserCourse(userCourse);
    }

    @Override
    public User getUser(Long num)
    {
        return userDao.getUser(num);
    }

    @Override
    public User findAUser(Long num)
    {
        return userDao.findAUser(num);
    }

    @Override
    public Boolean existUser(Long num)
    {
        return userDao.existUser(num);
    }

    @Override
    public UserCourse getUserCourse(Long username,Integer courseId)
    {
        return userDao.getUserCourse(username,courseId);
    }

    @Override
    public Boolean hasATeam(Long username,Integer courseId)
    {
        UserCourse userCourse = userDao.getUserCourse(username,courseId);
        //System.out.println(userCourse);
        return userCourse.getTeam_id() != 0;
    }

    @Override
    public Boolean userMatchCourse(Long username, Integer courseId)
    {
        UserCourse userCourse = userDao.getUserCourse(username,courseId);
        //System.out.println(userCourse);
        return userCourse == null;
    }

    @Override
    public List<UserCourse> getUserCourses(Long u)
    {
        return userDao.getUserCourses(u);
    }

    @Override
    public void updateQQ(Long u, String qq)
    {
        userDao.updateQQ(u,qq);
    }

    @Override
    public List<User> findUsersByClass(Integer c)
    {
        return userDao.findUsersByClass(c);
    }

    @Override
    public void updateMessage(Long u)
    {
        userDao.updateMessage(u);
    }
}
