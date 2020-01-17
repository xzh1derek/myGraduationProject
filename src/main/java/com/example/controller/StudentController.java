package com.example.controller;
import com.example.domain.Account;
import com.example.domain.User;
import com.example.service.IAccountService;
import com.example.service.IMailService;
import com.example.service.IUserService;
import jxl.Sheet;
import jxl.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("students")
public class StudentController
{
    @Autowired
    private IUserService userService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IMailService mailService;

    /**
     * 查看所有学生列表 分页查询
     * @param rows 每行显示数
     * @param page 当前页码
     * @return 学生的List
     */
    @RequestMapping("")
    public List<User> queryUserList(Integer rows, Integer page)
    {
        return userService.queryUserListPaging(rows,page);
    }

    /**
     * 获得分页总页码数
     * @param rows 每行显示数
     */
    @RequestMapping("/pages")
    public Integer queryUserPages(Integer rows)
    {
        return userService.queryUserNumbers()/rows;
    }

    /**
     * excel导入学生名单，表头必须是“学号”，“姓名”，“班级”，“学院”，“年级”。导入失败则会进行事务回滚
     * @param multipartFile excel文件
     * @return 导入结果，正确则返回成功添加行数以及最后一行结果
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @Transactional
    public String excelToDatabase(@RequestParam("file") MultipartFile multipartFile)
    {
        if(multipartFile.isEmpty()){
            return "false";
        }
        User user = new User();
        Account account = new Account();
        try {
            File file = new File(getClass().getResource(".").getFile(), Objects.requireNonNull(multipartFile.getOriginalFilename()));
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
            int i = 1, j;// 第一个是列数，第二个是行数
            for (; i < rows; i++) {
                for (j = 0 ; j < columns; j++) {
                    String username = rs.getCell(j++, i).getContents();
                    user.setUsername(Long.parseLong(username));
                    account.setUsername(username);
                    user.setName(rs.getCell(j++, i).getContents());
                    user.setClass_id(Integer.parseInt(rs.getCell(j++, i).getContents()));
                    user.setSchool(rs.getCell(j++, i).getContents());
                    user.setYear(rs.getCell(j++,i).getContents());
                    userService.newUser(user);
                    account.setPassword("123");
                    account.setIdentity(1);
                    accountService.createAccount(account);
                }
            }
            return "导入成功，总共添加"+(i-2)+"行记录，最后一行记录为[学号："+user.getUsername()+"，姓名："+user.getName()+
                    "，班级："+user.getClass_id()+"，学院："+user.getSchool()+"，年级："+user.getYear()+"]";
        } catch (IOException e) {
            e.printStackTrace();
            return "文件打开错误";
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "导入失败，存在重复的学号或数据格式错误，导入终止。总共添加0行记录。错误的记录为[学号："+user.getUsername()+"，姓名："+user.getName()+
            "，班级："+user.getClass_id()+"，学院："+user.getSchool()+"，年级："+user.getYear()+"]";
        }
    }

    /**
     * 添加一个学生
     * @param user 实体
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String addUser(@RequestBody User user)
    {
        userService.newUser(user);
        Account account = new Account();
        account.setUsername(user.getUsername().toString());
        account.setPassword("123");
        account.setIdentity(1);
        accountService.createAccount(account);
        return "0";
    }

    /**
     * 批量删除学生
     * @param userId 学号的数组
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public String deleteUser(@RequestBody Long[] userId)
    {
        for(Long username : userId)
        {
            userService.deleteUser(username);
            accountService.deleteAccount(username.toString());
        }
        return "0";
    }

    /**
     * 修改一个学生信息
     * @param userId 原学号
     * @param user 实体
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String updateUser(Long userId, @RequestBody User user)
    {
        userService.updateUser(userId, user);
        return "0";
    }

    /**
     * 重置学生密码为 “123”
     * @param userId 学号
     */
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    public String updatePassword(Long userId)
    {
        accountService.updatePassword(userId.toString(),"123");
        mailService.sendMail(0L,userId,0,null,"密码已重置为123，请尽快修改密码");
        return "0";
    }

    /**
     * 管理员批量给学生发布公告
     * @param text 公告文
     * @param userId 学号的数组
     */
    @RequestMapping(value = "/announce",method = RequestMethod.POST)
    public String sendAnnouncement(String text,@RequestBody Long[] userId)
    {
        for(Long username : userId)
        {
            mailService.sendMail(0L,username,0,null,text);
        }
        return "0";
    }
}
