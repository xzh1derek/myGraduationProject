package com.example.demo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class DemoApplicationTests
{
    @Test
    void test()
    {
        File dir = new File("target/classes/com/example/controller","course1");
        if(dir.exists()){
            File[] files = dir.listFiles();
            for(File file : files){
                file.delete();
            }
            dir.delete();
        }
        dir = new File("target/classes/com/example/controller","course_1_report");
        if(dir.exists()){
            File[] files = dir.listFiles();
            for(File file : files){
                file.delete();
            }
            dir.delete();
        }
    }
}
