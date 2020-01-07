package com.example.controller;

import com.example.domain.User;
import com.example.service.IUserService;
import jxl.Sheet;
import jxl.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("user")
public class UserController
{
    @Autowired
    private IUserService userService;

    /**
     * excel导入学生名单，表头必须是“学号”，“姓名”，“班级”，“学院”，“年级”
     * @param multipartFile excel文件
     * @return 导入结果，正确则返回成功添加行数以及最后一行结果
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String excelToDatabase(@RequestParam("file") MultipartFile multipartFile)
    {
        if(multipartFile.isEmpty()){
            return "false";
        }
        int i = 1, j;// 第一个是列数，第二个是行数
        User user = new User();
        try {
            File file = new File(getClass().getResource(".").getFile().toString(), Objects.requireNonNull(multipartFile.getOriginalFilename()));
            multipartFile.transferTo(file);
            Workbook rwb = Workbook.getWorkbook(file);
            Sheet rs = rwb.getSheet(0);
            int columns = rs.getColumns();// 得到所有的列
            int rows = rs.getRows();// 得到所有的行
            if(!rs.getCell(0,0).getContents().equals("学号")) return "错误，第一列不是'学号'";
            if(!rs.getCell(1,0).getContents().equals("姓名")) return "错误，第二列不是'姓名'";
            if(!rs.getCell(2,0).getContents().equals("班级")) return "错误，第三列不是'班级'";
            if(!rs.getCell(3,0).getContents().equals("学院")) return "错误，第四列不是'学院'";
            if(!rs.getCell(4,0).getContents().equals("年级")) return "错误，第四列不是'年级'";
            for (; i < rows; i++) {
                for (j = 0 ; j < columns; j++) {
                    user.setUsername(Long.parseLong(rs.getCell(j++, i).getContents()));
                    user.setName(rs.getCell(j++, i).getContents());
                    user.setClass_id(Integer.parseInt(rs.getCell(j++, i).getContents()));
                    user.setSchool(rs.getCell(j++, i).getContents());
                    user.setYear(rs.getCell(j++,i).getContents());
                    userService.newUser(user);
                }
            }
            return "导入成功，总共添加"+(i-2)+"行记录，最后一行记录为[学号："+user.getUsername()+"，姓名："+user.getName()+
                    "，班级："+user.getClass_id()+"，学院："+user.getSchool()+"，年级："+user.getYear()+"]";
        } catch (IOException e) {
            e.printStackTrace();
            return "文件打开错误";
        } catch (Exception e) {
            e.printStackTrace();
            return "导入失败，存在重复的学号或数据格式错误，导入终止。成功添加"+(i-1)+"行记录，错误的记录为：[学号："+user.getUsername()+
                    "，姓名："+user.getName()+"，班级："+user.getClass_id()+"，学院："+user.getSchool()+"，年级："+user.getYear()+"]";
        }
    }

    /**
     * 添加一个学生
     * @param user 实体
     * @return 状态码
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String addUser(@RequestBody User user)
    {
        userService.newUser(user);
        return "0";
    }

    /**
     * 批量删除学生
     * @param userId 学号的数组
     * @return 状态码
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public String deleteUser(@RequestBody Long[] userId)
    {
        for(Long username : userId)
        {
            userService.deleteUser(username);
        }
        return "0";
    }

    /**
     * 修改一个学生信息
     * @param userId 原学号
     * @param user 实体
     * @return 状态码
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String updateUser(Long userId, @RequestBody User user)
    {
        userService.updateUser(userId, user);
        return "0";
    }
}
