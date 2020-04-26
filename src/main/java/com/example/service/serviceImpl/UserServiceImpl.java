package com.example.service.serviceImpl;

import com.example.dao.IUserDao;
import com.example.domain.User;
import com.example.domain.UserCourse;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService
{
    @Autowired
    private IUserDao userDao;

    @Override
    public void updateIsLeader(Long username, Integer courseId, Boolean isLeader)
    {
        UserCourse userCourse = new UserCourse();
        userCourse.setUsername(username);
        userCourse.setCourse_id(courseId);
        userCourse.setIs_leader(isLeader);
        userDao.updateIsLeader(userCourse);
    }

    @Override
    public void updateTeamId(Long username, Integer courseId, Integer teamId)
    {
        UserCourse userCourse = new UserCourse();
        userCourse.setUsername(username);
        userCourse.setCourse_id(courseId);
        userCourse.setTeam_id(teamId);
        userDao.updateTeamId(userCourse);
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
    public UserCourse getUserCourseWithUser(Long username,Integer courseId)
    {
        return userDao.getUserCourse(username,courseId);
    }

    @Override
    public UserCourse queryUserCourseWithCourse(Long username, Integer courseId)
    {
        return userDao.queryUserCourseWithCourse(username,courseId);
    }

    @Override
    public List<Long> queryUsernameByTeamId(Integer teamId)
    {
        return userDao.queryUsernameByTeamId(teamId);
    }

    @Override
    public Boolean hasATeam(Long username,Integer courseId)
    {
        UserCourse userCourse = userDao.getUserCourse(username,courseId);
        return userCourse.getTeam_id() != 0;
    }

    @Override
    public Boolean userMatchCourse(Long username, Integer courseId)
    {
        UserCourse userCourse = userDao.queryUserCourse(username,courseId);
        return userCourse != null;
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
    public List<Long> findUsersByClass(Integer c)
    {
        return userDao.findUsersByClass(c);
    }

    @Override
    public void newUser(User user)
    {
        userDao.newUser(user);
    }

    @Override
    public void deleteUser(Long username)
    {
        userDao.deleteUser(username);
    }

    @Override
    public void updateUser(Long userId, User user)
    {
        userDao.updateUser(userId, user);
    }

    @Override
    public List<User> queryUserListPaging(Integer rows,Integer page)
    {
        return userDao.queryUserListPaging(rows,page);
    }

    @Override
    public Integer queryUserNumbers()
    {
        return userDao.queryUserNumbers();
    }

    @Override
    public List<User> sqlStudent(Map<String, String> map)
    {
        return userDao.sqlStudent(map);
    }

    @Override
    public Integer queryStudentsRecords(Map<String, String> map)
    {
        return userDao.queryStudentsRecords(map);
    }

    @Override
    public void updateScore(Long username, Integer courseId, Float score,String teacher)
    {
        userDao.updateScore(username,courseId,score,teacher);
    }

    @Override
    public List<UserCourse> queryUserCourseDynamically(Map<String, String> map)
    {
        return userDao.queryUserCourseDynamically(map);
    }

    @Override
    public Integer queryUserCourseRecords(Map<String, String> map)
    {
        return userDao.queryUserCourseRecords(map);
    }

    @Override
    public Map<String, Object> queryScoreData(Integer courseId)
    {
        return userDao.queryScoreData(courseId);
    }
}
