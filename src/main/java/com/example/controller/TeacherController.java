package com.example.controller;

import com.example.domain.Account;
import com.example.domain.Teacher;
import com.example.service.IAccountService;
import com.example.service.ITeacherService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("teachers")
public class TeacherController
{
    @Autowired
    private ITeacherService teacherService;
    @Autowired
    private IAccountService accountService;

    /**
     * 查询Teacher的List
     */
    @RequestMapping("")
    public List<Teacher> queryTeacherList()
    {
        return teacherService.findAllTeachers();
    }

    /**
     * 添加一个Teacher账号
     * @param username 账号名
     * @param name 姓名
     * @param password 密码
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String createTeacher(String username,String name,String password)
    {
        teacherService.createTeacher(username,name);
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(new Md5Hash(password).toString());
        account.setIdentity(2);
        accountService.createAccount(account);
        return "0";
    }

    /**
     * 删除一个老师
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public String deleteTeacher(Integer id)
    {
        teacherService.deleteTeacher(id);
        return "0";
    }

    /**
     * 给老师赋予管理权限
     * @param id 老师id
     */
    @RequestMapping(value = "/authorize",method = RequestMethod.POST)
    public String authorizeTeacher(Integer id)
    {
        Teacher teacher = teacherService.getTeacher(id);
        accountService.updateIdentity(teacher.getUsername(),0);
        teacherService.updateIdentity(id,"管理员");
        return "0";
    }
}
