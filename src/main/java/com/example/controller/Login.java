package com.example.controller;
import com.example.domain.Account;
import com.example.service.IAccountService;
import com.example.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class Login
{
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserService userService;

    /**
     * 登录功能 不用shiro
     * @param username 账号
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public Object login(String username,String password)
    {
        if(!accountService.existAccount(username)) return "n";//账号不存在
        Account account = accountService.getAccount(username);
        String psw = account.getPassword(),passwordEncrypted=new Md5Hash(password).toString();
        Integer identity = account.getIdentity();
        if(identity==1){
            if(passwordEncrypted.equals(psw)) return userService.getUser(Long.parseLong(username));//登陆成功，转入学生个人界面
            else return "f";//登录失败，重新登录
        }else{
            if(passwordEncrypted.equals(psw)) return "t";//转入老师/管理员界面
            else return "f";
        }
    }

    /**
     * 使用shiro的登录
     * @return
     */
    @RequestMapping(value = "loginShiro",method = RequestMethod.POST)
    @ResponseBody
    public Object loginMethod(String username,String password)
    {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,new Md5Hash(password).toString());
        try{
            subject.login(token);
            Account account = accountService.getAccount(username);
            if(account.getIdentity()==1) return userService.getUser(Long.parseLong(username));//转入学生页面
            else return "t";//转入老师管理员页面
        }catch(UnknownAccountException e) {
            e.printStackTrace();
            return "n";//用户不存在
        }catch(IncorrectCredentialsException e){
            e.printStackTrace();
            return "f";//密码错误
        }
    }

    @RequestMapping("index")
    public String index()
    {
        return "index";
    }

}
