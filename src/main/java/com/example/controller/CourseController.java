package com.example.controller;

import com.example.domain.Course;
import com.example.domain.User;
import com.example.service.ICourseService;
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

    /**
     * 返回所有课程
     * @return
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
     * @param teachers 老师
     * @param isTeam 是否成队
     * @param maxNum 最大人数(默认1，最高7)
     * @return 返回的是新建课程的id！！ 本地测试通过
     */
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public String newCourse(String code, String name, Float credit, Integer hours, Integer teachers, Boolean isTeam,
                            @RequestParam(defaultValue = "1")Integer maxNum)
    {
        Integer courseId = courseService.newCourse(code,name,credit,hours,teachers,isTeam,maxNum);
        System.out.println(courseId);
        return courseId.toString();
    }

    /**
     * 课程绑定学生
     * @param courseId 课程序号
     * @param classes 所选出班级的列表
     * @return 状态码 未测试
     */
    @RequestMapping(value = "/bind",method = RequestMethod.POST)
    public String bindStudents(Integer courseId, Integer[] classes)
    {
        Course course = courseService.getCourse(courseId);
        List<User> userList = new ArrayList<>();
        for(Integer classId : classes)
        {
            userList.addAll(userService.findUsersByClass(classId));
        }
        for(User user : userList)
        {
            courseService.newUserCourse(user.getUsername(),courseId,course.getHours());
        }
        courseService.updateStuNum(courseId,userList.size());
        return "0";
    }

    /**
     * 修改课程
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String updateCourse(Integer courseId,String code,String name,Float credit,Integer hours,Integer teachers,Boolean is_team,Integer max_num)
    {
        courseService.updateCourse(courseId,code,name,credit,hours,teachers,is_team,max_num);
        return "0";
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public String updateCourse(Integer courseId)
    {
        courseService.deleteCourse(courseId);
        return "0";
    }

}
