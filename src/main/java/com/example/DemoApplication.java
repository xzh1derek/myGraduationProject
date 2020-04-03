package com.example;

import com.example.domain.Project;
import com.example.mapper.CourseMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@SpringBootApplication
@MapperScan("com.example.mapper")//有了这个注解就不能使用xml配置mybatis，注解和xml方式二选一，不然就会报错
public class DemoApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DemoApplication.class, args);
    }
}
