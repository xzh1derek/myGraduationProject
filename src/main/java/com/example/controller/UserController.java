package com.example.controller;

import com.example.domain.Teacher;
import com.example.domain.User;
import com.example.service.IAccountService;
import com.example.service.ITeacherService;
import com.example.service.IUserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController
{
    @Autowired
    private IUserService userService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private ITeacherService teacherService;

    /**
     * 进入个人主界面
     * @param userId 当前用户学号
     * @return 返回用户信息 多表联查 一对多 本地测试通过
     */
    @RequestMapping("userInfo")
    public User userInfo(Long userId)
    {
        return userService.findAUser(userId);
    }

    /**
     * 修改qq号
     * @param userId 学号
     * @param qq QQ号
     * @return 状态码
     */
    @RequestMapping(value = "userInfo/updateQQ",method = RequestMethod.POST)
    public String updateQQ(Long userId, String qq)
    {
        userService.updateQQ(userId,qq);
        return "0";
    }

    /**
     * 修改密码
     * @param userId 账号
     * @param password 原密码
     * @return 状态码
     */
    @RequestMapping(value = "password/update",method = RequestMethod.POST)
    public String updatePassword(String userId,String password)
    {
        accountService.updatePassword(userId,new Md5Hash(password).toString());
        return "0";
    }

    /**
     * 返回老师数据
     * @param username 老师的id
     */
    @RequestMapping("teacherInfo")
    public Teacher getTeacher(String username)
    {
        return teacherService.queryTeacherByUsername(username);
    }
}
