package com.example.dao.daoImpl;

import com.example.dao.IUserDao;
import com.example.domain.User;
import com.example.domain.UserCourse;
import com.example.mapper.UserCourseMapper;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public User getUser(Long num)
    {
        return userMapper.getUser(num);
    }

    @Override
    public User findAUser(Long num)
    {
        return userRepository.getOne(num);
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

    @Override
    public UserCourse queryUserCourse(Long u, Integer c)
    {
        return userCourseMapper.queryUserCourse(u,c);
    }

    @Override
    public UserCourse queryUserCourseWithCourse(Long u, Integer c)
    {
        return userCourseMapper.queryUserCourseWithCourse(u,c);
    }

    @Override
    public List<UserCourse> getUserCourses(Long u)
    {
        return userCourseMapper.getUserCourses(u);
    }

    @Override
    public void updateIsLeader(UserCourse userCourse)
    {
        userCourseMapper.updateIsLeader(userCourse);
    }

    @Override
    public void updateTeamId(UserCourse userCourse)
    {
        userCourseMapper.updateTeamId(userCourse);
    }

    @Override
    public void deleteTeamMembers(Integer teamId)
    {
        userCourseMapper.deleteTeamMembers(teamId);
    }

    @Override
    public void updateQQ(Long u, String qq)
    {
        userMapper.updateQq(u,qq);
    }

    @Override
    public List<Long> findUsersByClass(Integer c)
    {
        return userMapper.findUsersByClass(c);
    }

    @Override
    public void saveUser(User user)
    {
        userRepository.save(user);
    }

    @Override
    public void newUser(User user)
    {
        userMapper.newUser(user);
    }

    @Override
    public void deleteUser(Long username)
    {
        userMapper.deleteUser(username);
    }

    @Override
    public void updateUser(Long userId, User user)
    {
        userMapper.updateUser(userId, user);
    }

    @Override
    public List<User> queryUserListPaging(Integer rows, Integer page)
    {
        return userMapper.queryUserListPaging(rows,rows*(page-1));
    }

    @Override
    public Integer queryUserNumbers()
    {
        return userMapper.queryUserNumbers();
    }

    @Override
    public List<Long> queryUsernameByTeamId(Integer teamId)
    {
        return userCourseMapper.queryUsernameByTeamId(teamId);
    }

    @Override
    public List<User> sqlStudent(Map<String, String> map)
    {
        return userMapper.queryStudentsDynamically(map);
    }

    @Override
    public Integer queryStudentsRecords(Map<String, String> map)
    {
        return userMapper.queryStudentsRecords(map);
    }

    @Override
    public void updateScore(Long username, Integer courseId, Float score,String teacher)
    {
        userCourseMapper.updateScore(username,courseId,score,teacher);
    }

    @Override
    public List<UserCourse> queryUserCourseDynamically(Map<String, String> map)
    {
        return userCourseMapper.sqlUserCourse(map);
    }

    @Override
    public Integer queryUserCourseRecords(Map<String, String> map)
    {
        return userCourseMapper.sqlUserCourseRecords(map);
    }

    @Override
    public Map<String,Object> queryScoreData(Integer courseId)
    {
        List<Float> scores = userCourseMapper.queryScoreNotNull(courseId);
        Map<String,Object> map = new LinkedHashMap<>();
        Integer hasScore = scores.size();
        Integer a=0,b=0,c=0,d=0,e=0;
        float sum=0f,max=Float.MIN_VALUE,min=Float.MAX_VALUE;
        for(Float score : scores){
            if(score>=90f){
                a++;
            }else if(score>=80){
                b++;
            }else if(score>=70){
                c++;
            }else if(score>=60){
                d++;
            }else{
                e++;
            }
            sum+=score;
            max=Math.max(max,score);
            min=Math.min(min,score);
        }
        map.put("hasScore",hasScore);
        map.put("90plus",a);
        map.put("80to90",b);
        map.put("70to80",c);
        map.put("60ro70",d);
        map.put("60below",e);
        map.put("average",sum/(float)hasScore);
        map.put("max",max);
        map.put("min",min);
        return map;
    }
}
