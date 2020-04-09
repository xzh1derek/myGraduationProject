package com.example.demo;
import com.example.mapper.UserMapper;
import com.example.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests
{
    @Autowired
    private UserMapper userMapper;

    @Test
    void test()
    {
    }
}
