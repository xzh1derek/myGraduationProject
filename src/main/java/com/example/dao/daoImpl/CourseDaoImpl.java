package com.example.dao.daoImpl;

import com.example.dao.ICourseDao;
import com.example.domain.*;
import com.example.mapper.*;
import com.example.repository.CourseRepository;
import org.apache.ibatis.annotations.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SchoolAndClassMapper schoolAndClassMapper;

    @Override
    public Integer newCourse(String code,String name,Float credit,Integer hours,Integer teacher,Boolean is_team,Integer max_num)
    {
        Course course = new Course();
        course.setCourse_code(code);
        course.setCourse_name(name);
        course.setCredit(credit);
        course.setHours(hours);
        course.setTeacher(teacher);
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
    public void updateCourse(Course course)
    {
        courseMapper.updateCourse(course);
    }

    @Override
    public void deleteCourse(Integer courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    public List<Course> queryCoursesWithProjects(Integer teacher)
    {
        return courseMapper.queryCourseWithProjects(teacher);
    }

    @Override
    public List<Project> queryProjectByCourse(Integer courseId)
    {
        return projectMapper.queryProjectsByCourse(courseId);
    }

    @Override
    public void deleteProject(Integer projectId)
    {
        projectMapper.deleteProject(projectId);
    }

    @Override
    public void deleteProjects(Integer courseId)
    {
        projectMapper.deleteProjects(courseId);
    }

    @Override
    public void newClassCourse(Integer classId,Integer courseId)
    {
        int schoolId=classId/1000%100;
        courseMapper.newClassCourse(classId,courseId,schoolId);
    }

    @Override
    public void deleteUserCourse(Integer courseId)
    {
        userCourseMapper.deleteUserCourse(courseId);
    }

    @Override
    public void deleteClassCourse(Integer courseId)
    {
        courseMapper.deleteClassCourse(courseId);
    }

    @Override
    public List<Course> queryCourseWithClasses()
    {
        return courseMapper.queryCourseWithClasses();
    }

    @Override
    public List<User> queryStudentsByCourse(Integer courseId)
    {
        List<UserCourse> userCourses = userCourseMapper.queryUserCourseByCourse(courseId);
        List<User> userList = new ArrayList<>();
        for(UserCourse userCourse : userCourses)
        {
            userList.add(userMapper.findAUser(userCourse.getUsername()));
        }
        return userList;
    }

    @Override
    public List<School> querySchoolWithClassesLimited(Integer courseId)
    {
        List<School> schools = schoolAndClassMapper.querySchool();
        for(School school : schools)
        {
            school.setClassesList(schoolAndClassMapper.queryClassBySchoolAndCourse(school.getId(),courseId));
        }
        return schools;
    }
}
