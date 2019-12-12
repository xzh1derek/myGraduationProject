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
     * @param password 密码
     * @return 状态码
     */
    @RequestMapping(value = "updatePassword",method = RequestMethod.POST)
    @ResponseBody
    public String updatePassword(String userId,String password)
    {
        accountService.updatePassword(userId,password);
        return "0";
    }
}
