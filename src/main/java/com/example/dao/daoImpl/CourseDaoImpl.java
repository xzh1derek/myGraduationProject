package com.example.dao.daoImpl;

import com.example.dao.ICourseDao;
import com.example.domain.Course;
import com.example.domain.Project;
import com.example.domain.UserCourse;
import com.example.mapper.CourseMapper;
import com.example.mapper.ProjectMapper;
import com.example.mapper.UserCourseMapper;
import com.example.repository.CourseRepository;
import org.apache.ibatis.annotations.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CourseDaoImpl implements ICourseDao
{
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserCourseMapper userCourseMapper;
    @Autowired
    private ProjectMapper projectMapper;

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
        userCourseMapper.newCourseList(userCourse);
    }

    @Override
    public void updateStuNum(Integer courseId, Integer num)
    {
        courseMapper.updateStuNum(courseId,num);
    }

    @Override
    public List<Course> queryCourseList() {
        return courseRepository.findAll();
    }

    @Override
    public void updateCourse(Integer courseId,String code,String name,Float credit,Integer hours,Integer teachers,Boolean is_team,Integer max_num) {
        Course course = new Course();
        course.setId(courseId);
        course.setCourse_code(code);
        course.setCourse_name(name);
        course.setCredit(credit);
        course.setHours(hours);
        course.setTeachers(teachers);
        course.setIs_team(is_team);
        course.setMax_num(max_num);
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Integer courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    public List<Course> queryCoursesWithProjects(Integer teacher)
    {
        return courseMapper.getCoursesWithProjects(teacher);
    }

    @Override
    public List<Project> queryProjectByCourse(Integer courseId)
    {
        return projectMapper.queryProjectsByCourse(courseId);
    }

    @Override
    public void deleteProjects(Integer courseId)
    {
        projectMapper.deleteProjects(courseId);
    }
}
