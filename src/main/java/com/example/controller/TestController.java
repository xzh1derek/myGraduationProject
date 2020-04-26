package com.example.controller;

import com.example.domain.User;
import com.example.mapper.*;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@RestController
@RequestMapping("test")
public class TestController
{
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private UserCourseMapper userCourseMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AccountMapper accountMapper;

    /**测试功能
     * 删除所有队伍，清空组队状态
     * @return 本地测试通过
     */
    @DeleteMapping(value = "/teams")
    public String deleteTeams()
    {
        teamMapper.deleteTeams();
        userCourseMapper.deleteMembers();
        return "0";
    }

    /**测试功能
     * 文件上传 打印文件名
     * @param multipartFile 文件
     * @return 成功则返回success
     */
//    @RequestMapping(value = "test/upload",method = RequestMethod.POST)
//    public String excelToDatabase(@RequestParam("file") MultipartFile multipartFile)
//    {
//        if (multipartFile.isEmpty()) {
//            return "false";
//        }
//        System.out.println(multipartFile.getOriginalFilename());
//        int i = 1, j;// 第一个是列数，第二个是行数
//        User user = new User();
//        try {
//            File file = new File(getClass().getResource(".").getFile().toString(), Objects.requireNonNull(multipartFile.getOriginalFilename()));
//            multipartFile.transferTo(file);
//            Workbook rwb = Workbook.getWorkbook(file);
//            Sheet rs = rwb.getSheet(0);
//            int columns = rs.getColumns();// 得到所有的列
//            int rows = rs.getRows();// 得到所有的行
//            if(!rs.getCell(0,0).getContents().equals("学号")) return "错误，第一列不是'学号'";
//            if(!rs.getCell(1,0).getContents().equals("姓名")) return "错误，第二列不是'姓名'";
//            if(!rs.getCell(2,0).getContents().equals("班级")) return "错误，第三列不是'班级'";
//            if(!rs.getCell(3,0).getContents().equals("学院")) return "错误，第四列不是'学院'";
//            if(!rs.getCell(4,0).getContents().equals("年级")) return "错误，第四列不是'年级'";
//            for (; i < rows; i++) {
//                for (j = 0 ; j < columns; j++) {
//                    user.setUsername(Long.parseLong(rs.getCell(j++, i).getContents()));
//                    user.setName(rs.getCell(j++, i).getContents());
//                    user.setClass_id(Integer.parseInt(rs.getCell(j++, i).getContents()));
//                    user.setSchool(rs.getCell(j++, i).getContents());
//                    user.setYear(rs.getCell(j++,i).getContents());
//                    //System.out.println(user);
//                }
//            }
//            return "导入成功，总共添加"+(i-2)+"行记录，最后一行记录为[学号："+user.getUsername()+"，姓名："+user.getName()+
//                    "，班级："+user.getClass_id()+"，学院："+user.getSchool()+"，年级："+user.getYear()+"]";
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "文件打开错误";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "导入失败，存在重复的学号或数据格式错误，导入终止。成功添加"+(i-1)+"行记录，错误的记录为：[学号："+user.getUsername()+
//                    "，姓名："+user.getName()+"，班级："+user.getClass_id()+"，学院："+user.getSchool()+"，年级："+user.getYear()+"]";
//        }
//    }

    /**
     * 删库接口 删除所有学生以及账号
     */
    @DeleteMapping(value = "/students")
    public String deleteAllUsers()
    {
        userMapper.deleteAllUsers();
        accountMapper.deleteAccountByIdentity(1);
        return "0";
    }

    /**
     * 删库接口 删除所有user_course
     */
    @DeleteMapping(value = "/userCourses")
    public String deleteAllUserCourses()
    {
        userCourseMapper.deleteAllUserCourses();
        return "0";
    }
}
