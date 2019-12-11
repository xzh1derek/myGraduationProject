package com.example.controller;

import com.example.domain.Account;
import com.example.service.IAccountService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class Login
{
    @Autowired
    private IAccountService accountService;

    /**
     * 登录功能 未测试
     * @param username 账号
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public String login(String username,String password)
    {
        if(!accountService.existAccount(username)) return "n";//账号不存在
        Account account = accountService.getAccount(username);
        String psw = account.getPassword();
        Integer identity = account.getIdentity();
        if(identity==0){
            if(password.equals(psw)) return "s";//登陆成功，转入学生个人界面
            else return "f";//登录失败，重新登录
        }else{
            if(password.equals(psw)) return "a";//转入管理员界面
            else return "f";
        }
    }

    @RequestMapping("index")
    public String index()
    {
        return "index";
    }

}
