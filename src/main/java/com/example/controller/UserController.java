package com.example.controller;

import com.example.domain.User;
import com.example.service.IAccountService;
import com.example.service.IUserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("userInfo")
public class UserController
{
    @Autowired
    private IUserService userService;
    @Autowired
    private IAccountService accountService;

    /**
     * 进入个人主界面
     * @param userId 当前用户学号
     * @return 返回用户信息 多表联查 一对多 本地测试通过
     */
    @RequestMapping("")
    public User userInfo(Long userId)
    {
        return userService.getUser(userId);
    }

    /**
     * 修改qq号
     * @param userId 学号
     * @param qq QQ号
     * @return 状态码
     */
    @RequestMapping(value = "/updateQQ",method = RequestMethod.POST)
    public String updateQQ(Long userId, String qq)
    {
        userService.updateQQ(userId,qq);
        return "0";
    }

    /**
     * 修改密码
     * @param userId 账号
     * @param password 原密码
     * @param newPassword 新密码
     * @return 状态码
     */
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    public String updatePassword(String userId,String password,String newPassword)
    {
        if(!password.equals("123")){
            password = new Md5Hash(password).toString();
        }
        //System.out.println("原密码(加密后)："+password);
        String newPasswordEncrypted = new Md5Hash(newPassword).toString();
        //System.out.println("新密码(加密后)："+newPasswordEncrypted);
        if(!accountService.getAccount(userId).getPassword().equals(password)) return "f";//原密码输错
        accountService.updatePassword(userId,newPasswordEncrypted);
        return "0";
    }
}
