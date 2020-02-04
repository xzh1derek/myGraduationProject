package com.example.demo;

import com.example.config.utils.ByteConverter;
import com.example.domain.User;
import com.example.mapper.CourseMapper;
import com.example.mapper.ModuleMapper;
import com.example.mapper.UserCourseMapper;
import jxl.Sheet;
import jxl.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootTest
class DemoApplicationTests
{
    @Autowired
    private JedisPool jedisPool;

    @Test
    void test()
    {
        System.out.println(jedisPool);
    }
}
