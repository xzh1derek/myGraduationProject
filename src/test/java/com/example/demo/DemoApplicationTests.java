package com.example.demo;

import com.example.domain.User;
import com.example.mapper.ModuleMapper;
import com.example.mapper.UserCourseMapper;
import jxl.Sheet;
import jxl.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest
class DemoApplicationTests
{

    @Test
    void test()
    {
        List<User> userList = new ArrayList<>();
        try {
            File file = new File("C:\\Users\\DerekChen\\Desktop\\userInfoList.xls");
            Workbook rwb = Workbook.getWorkbook(file);
            Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
            int columns = rs.getColumns();// 得到所有的列
            int rows = rs.getRows();// 得到所有的行
            //System.out.println("列数" + columns + "行数:" + rows);
            for (int i = 1; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    // 第一个是列数，第二个是行数
                    User user = new User();
                    user.setUsername(Long.parseLong(rs.getCell(j++, i).getContents()));
                    user.setName(rs.getCell(j++, i).getContents());
                    user.setClass_id(Integer.parseInt(rs.getCell(j++, i).getContents()));
                    user.setSchool(rs.getCell(j++, i).getContents());
                    user.setYear(rs.getCell(j++,i).getContents());
                    //System.out.println(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
