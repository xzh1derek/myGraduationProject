package com.example.controller;

import com.example.service.ICourseService;
import com.example.service.ITeacherService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("score")
public class ScoreController
{
    @Autowired
    private IUserService userService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private ITeacherService teacherService;

}
