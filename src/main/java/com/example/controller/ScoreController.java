package com.example.controller;

import com.example.domain.Course;
import com.example.domain.User;
import com.example.domain.UserCourse;
import com.example.service.ICourseService;
import com.example.service.ITeacherService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("score")
public class ScoreController
{
    @Autowired
    private IUserService userService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private ITeacherService teacherService;

    /**
     * 查询某个老师下已发布的课程
     * @param teacherId 老师id
     */
    @RequestMapping("")
    public List<Course> queryCourseByTeacher(Integer teacherId)
    {
        return courseService.queryCourseByTeacher(teacherId);
    }

    /**
     * 查询学生成绩 只显示有成绩的 按条件筛查 分页查询
     */
    @RequestMapping("/search")
    public List<UserCourse> queryUserCourseHasScore(String courseId, String username, String name, String classId, String school, String teamId,
                                                    @RequestParam(defaultValue = "0") String marked, String order, String rows, String page)
    {
        Map<String,String> map = new HashMap<>();
        map.put("courseId",courseId);
        map.put("username",username);
        map.put("name",name);
        map.put("classId",classId);
        map.put("school",school);
        map.put("teamId",teamId);
        map.put("marked",marked);
        map.put("order",order);
        map.put("rows",rows);
        map.put("page",page);
        return userService.queryUserCourseDynamically(map);
    }

    /**
     * 获得记录条数
     */
    @RequestMapping("/search/pages")
    public Integer queryUserCourseHasScoreRecords(String courseId,String username,String name,String classId,
                                                  @RequestParam(defaultValue = "0")String marked,String school,String teamId)
    {
        Map<String,String> map = new HashMap<>();
        map.put("courseId",courseId);
        map.put("username",username);
        map.put("name",name);
        map.put("classId",classId);
        map.put("school",school);
        map.put("teamId",teamId);
        map.put("marked",marked);
        return userService.queryUserCourseRecords(map);
    }

    /**
     * 查询成绩统计数据
     * @param courseId 课程id
     */
    @RequestMapping("/data")
    public Map<String,Object> queryScoreData(Integer courseId)
    {
        return userService.queryScoreData(courseId);
    }

    /**
     * 修改成绩
     * @param courseId 课程id
     * @param userId 学号
     * @param score 成绩
     * @param teacherId 老师id
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String updateScore(Integer courseId,Long userId,Float score,Integer teacherId)
    {
        String name = teacherService.getTeacher(teacherId).getName();
        UserCourse userCourse = userService.queryUserCourseWithCourse(userId,courseId);
        String teacher = userCourse.getTeacher();
        if(teacher!=null&&!teacher.contains(name)){
            userService.updateScore(userId,courseId,score,teacher+"、"+name);
        }
        else userService.updateScore(userId,courseId,score,teacher);
        return "0";
    }

    /**
     * 导出成绩单
     * @param courseId 课程id
     */
    @RequestMapping(value = "/export")
    public List<UserCourse> queryStudentsByCourse(Integer courseId)
    {
        return courseService.queryStudentsByCourse(courseId);
    }
}
