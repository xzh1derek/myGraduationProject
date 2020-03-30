package com.example.demo;
import com.example.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests
{
    @Autowired
    private IUserService userService;

    @Test
    void test()
    {
        System.out.println(userService.userMatchCourse(16010410031L,1));
    }
}
