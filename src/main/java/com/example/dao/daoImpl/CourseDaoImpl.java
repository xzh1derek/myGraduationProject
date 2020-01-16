package com.example.dao.daoImpl;

import com.example.config.utils.ByteConverter;
import com.example.dao.ICourseDao;
import com.example.domain.*;
import com.example.mapper.*;
import com.example.repository.CourseRepository;
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
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Integer newCourse(String code,String name,Float credit,Integer hours,Boolean is_team,Integer max_num)
    {
        Course course = new Course();
        course.setCourse_code(code);
        course.setCourse_name(name);
        course.setCredit(credit);
        course.setHours(hours);
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
    public List<Course> queryCourseList()
    {
        List<Course> courses = courseRepository.findAll();
        for(Course course : courses)
        {
            List<Teacher> teachers = new ArrayList<>();
            List<Integer> teacherIndex = ByteConverter.convertLongToIndex(course.getTeacher());
            for(Integer index : teacherIndex)
            {
                teachers.add(teacherMapper.getTeacher(index));
            }
            course.setTeachers(teachers);
        }
        return courses;
    }

    @Override
    public void updateCourse(Course course)
    {
        courseMapper.updateCourse(course);
    }

    @Override
    public void updateTeachers(Integer courseId, Long teacher)
    {
        courseMapper.updateTeachers(courseId,teacher);
    }

    @Override
    public void updateIsPublished(Integer id, Boolean status)
    {
        courseMapper.updateIsPublished(id,status);
    }

    @Override
    public void deleteCourse(Integer courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    public List<Course> queryCoursesWithProjects()
    {
        List<Course> courses = courseMapper.queryCourseWithProjects();
        for(Course course : courses)
        {
            List<Teacher> teachers = new ArrayList<>();
            List<Integer> teacherIndex = ByteConverter.convertLongToIndex(course.getTeacher());
            for(Integer index : teacherIndex)
            {
                teachers.add(teacherMapper.getTeacher(index));
            }
            course.setTeachers(teachers);
        }
        return courses;
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

    /**
     * 查询要上某个课程的学生名单 分页查询
     * @param courseId 课程id
     * @return 学生的List
     */
    @Override
    public List<UserCourse> queryStudentsByCoursePaging(Integer courseId, Integer rows, Integer page)
    {
        List<UserCourse> userCourses = userCourseMapper.queryUserCourseByCoursePaging(courseId,rows,rows*(page-1));
        for(UserCourse userCourse : userCourses)
        {
            userCourse.setUser(userMapper.findAUser(userCourse.getUsername()));
        }
        return userCourses;
    }

    /**
     * 查询要上某个课程的学生名单 一次性查询
     * @param courseId 课程id
     * @return 学生的List 此功能用于导出excel
     */
    @Override
    public List<UserCourse> queryStudentsByCourse(Integer courseId)
    {
        List<UserCourse> userCourses = userCourseMapper.queryUserCourseByCourse(courseId);
        for(UserCourse userCourse : userCourses)
        {
            userCourse.setUser(userMapper.findAUser(userCourse.getUsername()));
        }
        return userCourses;
    }

    /**
     * 查询某一个课程绑定了的班级，并以学院为单位列出
     * @param courseId 课程id
     * @return 学院的List，带上班级的List，手动注入
     */
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

    @Override
    public List<Integer> queryClassByCourse(Integer course_id)
    {
        return schoolAndClassMapper.queryClassByCourse(course_id);
    }
}
