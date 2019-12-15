package com.example.controller;

import com.example.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccountController
{
    @Autowired
    private IAccountService accountService;

    /**
     * 修改密码
     * @param userId 账号
     * @param password 原密码
     * @param newPassword 新密码
     * @return 状态码
     */
    @RequestMapping(value = "updatePassword",method = RequestMethod.POST)
    @ResponseBody
    public String updatePassword(String userId,String password,String newPassword)
    {
        if(!accountService.getAccount(userId).getPassword().equals(password)) return "f";//原密码输错
        accountService.updatePassword(userId,newPassword);
        return "0";
    }
}
