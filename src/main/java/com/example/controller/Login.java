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
    private IUserService userService;
    @Autowired
    private IAccountService accountService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public String login(String username,String password)
    {
        if(!accountService.existAccount(username)) return "n";//账号不存在
        Account account = accountService.getAccount(username);
        String psw = account.getPassword();
        Integer identity = account.getIdentity();
        if(identity==1){
            if(psw.equals("123")){
                if(password.equals(psw)) return "r";//登陆成功，转入注册个人信息界面
                else return "f";//登录失败，重新登录
            }
            else{
                if(password.equals(psw)) return "i";//登陆成功，转入个人主界面
                else return "f";
            }
        }
        else if(identity==2){
            if(password.equals(psw)) return "t";//转入教师界面
            else return "f";
        }else if(identity==0){
            if(password.equals(psw)) return "a";//转入管理员界面
            else return "f";
        }
        return "0";
    }

    @RequestMapping(value = "registry",method = RequestMethod.POST)
    @ResponseBody
    public String registry(String username, String password, String name, String school, String qq)
    {
        userService.saveUser(username,password,name,school,qq);
        return "i";
    }

    @RequestMapping("index")
    public String index()
    {
        return "index";
    }

}
