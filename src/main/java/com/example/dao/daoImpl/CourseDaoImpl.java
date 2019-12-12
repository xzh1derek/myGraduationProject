package com.example.dao.daoImpl;

import com.example.dao.ICourseDao;
import com.example.domain.Course;
import com.example.domain.UserCourse;
import com.example.mapper.CourseMapper;
import com.example.repository.CourseRepository;
import com.example.repository.UserCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDaoImpl implements ICourseDao
{
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserCourseRepository userCourseRepository;

    @Override
    public Integer newCourse(String code,String name,Float credit,Integer hours,Integer teachers,Boolean is_team,Integer max_num)
    {
        Course course = new Course();
        course.setCourse_code(code);
        course.setCourse_name(name);
        course.setCredit(credit);
        course.setHours(hours);
        course.setTeachers(teachers);
        course.setIs_team(is_team);
        course.setMax_num(max_num);
        courseMapper.newCourse(course);
        return course.getId();
    }

    @Override
    public Course getCourse(Integer courseId)
    {
        return courseRepository.getOne(courseId);
    }

    @Override
    public void newUserCourse(UserCourse userCourse)
    {
        userCourseRepository.save(userCourse);
    }

    @Override
    public void updateStuNum(Integer courseId, Integer num)
    {
        courseMapper.updateStuNum(courseId,num);
    }
}
