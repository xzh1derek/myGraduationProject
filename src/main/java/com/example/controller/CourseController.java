package com.example.controller;

import com.example.domain.Course;
import com.example.domain.Project;
import com.example.domain.User;
import com.example.domain.UserCourse;
import com.example.service.ICourseService;
import com.example.service.IModuleService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("course")
public class CourseController
{
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IModuleService moduleService;

    /**
     * 返回所有课程
     * @return 课程的List
     */
    @RequestMapping("")
    public List<Course> showCourses()
    {
        return courseService.queryCourseList();
    }

    /**
     * 管理员开设一门课程
     * @param code 课程编号
     * @param name 课程名称
     * @param credit 学分
     * @param hours 总学时
     * @param teacher 老师
     * @param isTeam 是否成队
     * @param maxNum 最大人数(默认1，最高7)
     * @return 返回的是新建课程的id！！ 测试通过
     */
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public String newCourse(String code, String name, Float credit, Integer hours, Integer teacher, Boolean isTeam,
                            @RequestParam(defaultValue = "1")Integer maxNum)
    {
        Integer courseId = courseService.newCourse(code,name,credit,hours,teacher,isTeam,maxNum);
        return courseId.toString();
    }

    /**
     * 课程绑定班级
     * @param id 课程序号
     * @param classes 所选出班级的列表
     * @return 状态码 测试通过
     */
    @RequestMapping(value = "/bind",method = RequestMethod.POST)
    public String bindStudents(Integer id,@RequestBody Integer[] classes)
    {
        List<Long> usernameList = new ArrayList<>();
        for(Integer classId : classes)
        {
            courseService.newClassCourse(classId,id);
            usernameList.addAll(userService.findUsersByClass(classId));
        }
        UserCourse userCourse = new UserCourse();
        userCourse.setCourse_id(id);
        for(Long username : usernameList)
        {
            userCourse.setUsername(username);
            courseService.newUserCourse(userCourse);
        }
        courseService.updateStuNum(id,usernameList.size());
        return "0";
    }

    /**
     * 修改课程
     * @return 状态码
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String updateCourse(@RequestBody Course course)
    {
        courseService.updateCourse(course);
        return "0";
    }

    /**
     * 删除课程 同时删除绑定的班级和学生 同时删除旗下的projects和modules
     * @param courseId 课程编号
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public String updateCourse(Integer courseId)
    {
        courseService.deleteCourse(courseId);
        courseService.deleteClassCourse(courseId);
        courseService.deleteUserCourse(courseId);
        List<Project> projects = courseService.queryProjectByCourse(courseId);
        for(Project project : projects)
        {
            moduleService.deleteModules(project.getId());
        }
        courseService.deleteProjects(courseId);
        return "0";
    }

    /**
     * 查询所有课程 同时返回课程下的所有已绑班级
     * @return 课程的List
     */
    @RequestMapping(value = "/classes")
    public List<Course> queryCourseWithClasses()
    {
        return courseService.queryCourseWithClasses();
    }

    /**
     * 查询该课程的所有学生
     * @param courseId 课程号
     * @return 学生的List
     */
    @RequestMapping(value = "/students")
    public List<User> queryStudentsByCourse(Integer courseId)
    {
        return courseService.queryStudentsByCourse(courseId);
    }

}
