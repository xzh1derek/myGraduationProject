package com.example.controller;

import com.example.mapper.TeamMapper;
import com.example.mapper.UserCourseMapper;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController
{
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private UserCourseMapper userCourseMapper;

    /**测试功能
     * 删除所有队伍，清空组队状态
     * @return 本地测试通过
     */
    @RequestMapping(value = "delete",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteTeam()
    {
        teamMapper.deleteTeams();
        userCourseMapper.deleteMembers();
        return "0";
    }
}
