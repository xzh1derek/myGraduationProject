package com.example.demo;

import com.example.mapper.ModuleMapper;
import com.example.mapper.UserCourseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests
{
    @Autowired
    private ModuleMapper moduleMapper;

    @Test
    void test()
    {
        Integer id =1;
        System.out.println(moduleMapper.queryModulesWithProjectAndCourse(id));
    }

}
