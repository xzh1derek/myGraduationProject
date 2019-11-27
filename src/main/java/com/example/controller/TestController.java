package com.example.controller;

import com.example.mapper.TeamMapper;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController
{
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("delete")
    @ResponseBody
    public String deleteTeam()
    {
        teamMapper.deleteTeams();
        userMapper.deleteMembers();
        return "0";
    }
}
