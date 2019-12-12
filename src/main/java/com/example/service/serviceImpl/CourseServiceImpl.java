package com.example.service.serviceImpl;

import com.example.dao.ICourseDao;
import com.example.domain.Course;
import com.example.domain.UserCourse;
import com.example.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements ICourseService
{
    @Autowired
    private ICourseDao courseDao;

    @Override
    public Integer newCourse(String code,String name,Float credit,Integer hours,Integer teachers,Boolean is_team,Integer max_num)
    {
        return courseDao.newCourse(code,name,credit,hours,teachers,is_team,max_num);
    }

    @Override
    public void newUserCourse(Long username, Integer courseId, Integer hours)
    {
        UserCourse userCourse = new UserCourse();
        userCourse.setUsername(username);
        userCourse.setCourse_id(courseId);
        userCourse.setHours_left(hours);
        userCourse.setIs_leader(false);
        userCourse.setTeam_id(0);
        courseDao.newUserCourse(userCourse);
    }

    @Override
    public void updateStuNum(Integer courseId, Integer num)
    {
        courseDao.updateStuNum(courseId,num);
    }

    @Override
    public Course getCourse(Integer courseId)
    {
        return courseDao.getCourse(courseId);
    }
}
