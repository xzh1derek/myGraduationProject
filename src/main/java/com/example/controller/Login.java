package com.example.controller;

import com.example.domain.User;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class Login
{
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam("username")String name,@RequestParam("password")String password)
    {
        Long username = Long.parseLong(name);
        if(!userService.existUser(username)) return "n";//用户不存在
        User user = userService.getUser(username);
        String psw = user.getPassword();
        if(psw.equals("123")){
            if(password.equals(psw)) return "r";//登陆成功，转入注册个人信息界面
            else return "f";//登录失败，重新登录
        }
        else{
            if(password.equals(psw)) return "i";//登陆成功，转入个人主界面
            else return "f";//登录失败，重新登录
        }
    }

    @RequestMapping(value = "registry",method = RequestMethod.POST)
    @ResponseBody
    public String login(String username, String password, String name, String school, String qq)
    {
        Long userId = Long.parseLong(username);
        userService.saveUser(userId,password,name,school,qq);
        return "i";
    }

}
