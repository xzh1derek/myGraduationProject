package com.example.controller;

import com.example.domain.User;
import com.example.service.IUserService;
import com.example.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("userInfo")
public class UserController
{
    @Autowired
    private IUserService userService;

    /**
     * 进入个人主界面
     * @param userId 当前用户学号
     * @return 返回用户信息 测试通过
     */
    @RequestMapping(value = "")
    @ResponseBody
    public User index(@RequestParam("userId") String userId)
    {
        Long num = Long.parseLong(userId);
        User user = userService.getUser(num);
        return user;
    }
}
