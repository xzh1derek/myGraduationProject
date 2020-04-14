package com.example.controller;
import com.example.config.utils.ByteConverter;
import com.example.domain.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private ITeacherService teacherService;
    @Autowired
    private IMailService mailService;

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
     * @param isTeam 是否成队
     * @param maxNum 最大人数(默认1，最高7)
     * @return 返回的是新建课程的id！！ 测试通过
     */
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public String newCourse(String code, String name, Float credit, Integer hours, Boolean isTeam,
                            @RequestParam(defaultValue = "1")Integer maxNum)
    {
        courseService.newCourse(code,name,credit,hours,isTeam,maxNum);
        return "0";
    }

    /**
     * 返回所有老师的List
     */
    @RequestMapping(value = "/teachers")
    public List<Teacher> queryTeacherList()
    {
        return teacherService.findAllTeachers();
    }

    /**
     * 课程绑定老师
     * @param courseId 课程编号
     * @param teachers 老师id的数组
     */
    @RequestMapping(value = "/teachers/bind",method = RequestMethod.POST)
    public String bindTeachers(Integer courseId,@RequestBody Integer[] teachers)
    {
        Long teachersEncoded = ByteConverter.convertIndexToLong(teachers);
        courseService.updateTeachers(courseId,teachersEncoded);
        return "0";
    }

    /**
     * 课程绑定班级 发布之后无法绑定
     * @param id 课程序号
     * @param classes 所选出班级的列表
     */
    @RequestMapping(value = "/bind",method = RequestMethod.POST)
    public String bindStudents(Integer id,@RequestBody Integer[] classes)
    {
        if(courseService.getCourse(id).getIs_published()) return "已发布课程，不能绑定班级";
        courseService.deleteClassCourse(id);
        for(Integer classId : classes)
        {
            courseService.newClassCourse(classId,id);
        }
        return "0";
    }

    /**
     * 发布课程 之前绑定好的班级的学生会全部注册这门课程 支持回滚
     * @param courseId 课程id
     */
    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    @Transactional
    public String publishCourse(Integer courseId)
    {
        Course course = courseService.getCourse(courseId);
        if(course.getIs_published()) return "已发布课程，不能再次发布";
        List<Long> usernameList = new ArrayList<>();
        List<Integer> classList = courseService.queryClassByCourse(courseId);
        if(classList.isEmpty()) return "未绑定班级，无法发布课程";
        for(Integer classId : classList)
        {
            usernameList.addAll(userService.findUsersByClass(classId));
        }
        UserCourse userCourse = new UserCourse();
        userCourse.setCourse_id(courseId);
        String text = "新增实验课程【"+course.getCourse_name()+"】，" +
                "学时【"+course.getHours()+"】，学分【"+course.getCredit()+"】";
        if(course.getIs_team()) text = text + "，本课程需要学生组队完成，请尽快组好队伍。";
        for(Long username : usernameList)
        {
            userCourse.setUsername(username);
            courseService.newUserCourse(userCourse);
            mailService.sendMail(0L,username,0,null,text);
        }
        courseService.updateStuNum(courseId,usernameList.size());
        courseService.updateIsPublished(courseId,true);
        return "0";
    }

    /**
     * 修改课程信息
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
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public String deleteCourse(Integer courseId)
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
     * 查询某个课程的所有已绑班级 放在学院下面
     * @return School的List
     */
    @RequestMapping(value = "/classes")
    public List<School> querySchoolWithClassLimited(Integer courseId)
    {
        return courseService.querySchoolWithClassesLimited(courseId);
    }

    /**
     * 查询该课程的所有学生 分页查询
     * @param courseId 课程号
     * @return 学生的List
     */
    @RequestMapping(value = "/students")
    public List<UserCourse> queryStudentsByCoursePaging(Integer courseId,Integer rows,Integer page)
    {
        return courseService.queryStudentsByCoursePaging(courseId,rows,page);
    }

    /**
     * 获得记录总数
     * @param courseId 课程id
     */
    @RequestMapping("/students/pages")
    public Integer queryStudentsPages(Integer courseId)
    {
        return courseService.queryStudentsPages(courseId);
    }

    /**
     * 查询该课程的所有学生 一次性查询
     * @param courseId 课程号
     * @return 学生的List 用于导出Excel
     */
    @RequestMapping(value = "/students/export")
    public List<UserCourse> queryStudentsByCourse(Integer courseId)
    {
        return courseService.queryStudentsByCourse(courseId);
    }

    /**
     * 查询该课程里有没有某个学生
     * @param courseId 课程号
     * @param userId 学号
     * @return 不存在则返回"n"，存在则返回该学生
     */
    @RequestMapping("/students/search")
    public Object searchStudentInCourse(Integer courseId,Long userId)
    {
        if(!userService.userMatchCourse(userId,courseId)) return "n";
        return userService.getUserCourseWithUser(userId,courseId);
    }

    /**
     * 给单个学生绑定课程
     * @param userId 学号
     * @param courseId 课程号
     * @return 状态码
     */
    @RequestMapping(value = "/students/add",method = RequestMethod.POST)
    public String bindAStudent(Long userId, Integer courseId)
    {
        UserCourse userCourse = new UserCourse();
        userCourse.setUsername(userId);
        userCourse.setCourse_id(courseId);
        courseService.newUserCourse(userCourse);
        return "0";
    }

    /**
     * 查询组队性质的课程
     */
    @RequestMapping(value = "/isTeam",method = RequestMethod.GET)
    public List<Course> queryCourseIsTeam()
    {
        return courseService.queryCourseIsTeam(true);
    }
}
