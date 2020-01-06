package com.example.controller;

import com.example.domain.User;
import jxl.Sheet;
import jxl.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("user")
public class UserController
{
    @RequestMapping("/upload")
    public String excelToDatabase(@RequestParam("fileName") MultipartFile multipartFile)
    {
        if(multipartFile.isEmpty()){
            return "false";
        }
        List<User> userList = new ArrayList<>();
        try {
            File file = new File(".", Objects.requireNonNull(multipartFile.getOriginalFilename()));
            multipartFile.transferTo(file);
            Workbook rwb = Workbook.getWorkbook(file);
            Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
            int columns = rs.getColumns();// 得到所有的列
            int rows = rs.getRows();// 得到所有的行
            System.out.println("列数" + columns + "行数:" + rows);
            for (int i = 1; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    // 第一个是列数，第二个是行数
                    User user = new User();
                    user.setUsername(Long.parseLong(rs.getCell(j++, i).getContents()));
                    user.setName(rs.getCell(j++, i).getContents());
                    user.setClass_id(Integer.parseInt(rs.getCell(j++, i).getContents()));
                    user.setSchool(rs.getCell(j++, i).getContents());
                    user.setYear(rs.getCell(j++,i).getContents());
                    System.out.println(user);
                    userList.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }
}
