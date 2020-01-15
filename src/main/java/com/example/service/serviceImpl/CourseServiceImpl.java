package com.example.service.serviceImpl;

import com.example.dao.ICourseDao;
import com.example.domain.*;
import com.example.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements ICourseService
{
    @Autowired
    private ICourseDao courseDao;

    @Override
    public Integer newCourse(String code,String name,Float credit,Integer hours,Boolean is_team,Integer max_num)
    {
        return courseDao.newCourse(code,name,credit,hours,is_team,max_num);
    }

    @Override
    public void newUserCourse(UserCourse userCourse)
    {
        courseDao.newUserCourse(userCourse);
    }

    @Override
    public void updateTeachers(Integer courseId, Long teacher)
    {
        courseDao.updateTeachers(courseId,teacher);
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

    @Override
    public List<Course> queryCourseList() {
        return courseDao.queryCourseList();
    }

    @Override
    public void updateCourse(Course course) {
        courseDao.updateCourse(course);
    }

    @Override
    public void deleteCourse(Integer courseId) {
        courseDao.deleteCourse(courseId);
    }

    @Override
    public List<Course> queryCoursesWithProjects()
    {
        return courseDao.queryCoursesWithProjects();
    }

    @Override
    public List<Project> queryProjectByCourse(Integer courseId)
    {
        return courseDao.queryProjectByCourse(courseId);
    }

    @Override
    public void deleteProject(Integer projectId)
    {
        courseDao.deleteProject(projectId);
    }

    @Override
    public void deleteProjects(Integer courseId)
    {
        courseDao.deleteProjects(courseId);
    }

    @Override
    public void newClassCourse(Integer classId,Integer courseId)
    {
        courseDao.newClassCourse(classId,courseId);
    }

    @Override
    public void deleteUserCourse(Integer courseId)
    {
        courseDao.deleteUserCourse(courseId);
    }

    @Override
    public void deleteClassCourse(Integer courseId)
    {
        courseDao.deleteClassCourse(courseId);
    }

    @Override
    public List<Course> queryCourseWithClasses()
    {
        return courseDao.queryCourseWithClasses();
    }

    @Override
    public List<UserCourse> queryStudentsByCoursePaging(Integer courseId, Integer rows, Integer page)
    {
        return courseDao.queryStudentsByCoursePaging(courseId,rows,page);
    }

    @Override
    public List<UserCourse> queryStudentsByCourse(Integer courseId)
    {
        return courseDao.queryStudentsByCourse(courseId);
    }

    @Override
    public List<School> querySchoolWithClassesLimited(Integer courseId)
    {
        return courseDao.querySchoolWithClassesLimited(courseId);
    }

    @Override
    public List<Integer> queryClassByCourse(Integer course_id)
    {
        return courseDao.queryClassByCourse(course_id);
    }
}
