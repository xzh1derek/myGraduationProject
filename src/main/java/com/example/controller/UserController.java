package com.example.controller;

import com.example.domain.User;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class UserController
{
    @Autowired
    private IUserService userService;

    /**
     * 进入个人主界面
     * @param userId 当前用户学号
     * @return 返回用户信息 多表联查 一对多 本地测试通过
     */
    @RequestMapping("userInfo")
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
    @RequestMapping(value = "updateQQ",method = RequestMethod.POST)
    public String updateQQ(Long userId, String qq)
    {
        userService.updateQQ(userId,qq);
        return "0";
    }
}
