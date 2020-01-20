package com.example.config.shiro;
import com.example.domain.Account;
import com.example.service.IAccountService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm
{
    @Autowired
    private IAccountService accountService;

    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection)
    {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("whatTheFuck");
        return info;
    }

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException
    {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        if(!accountService.existAccount(token.getUsername())) return null;//会抛出UnknownAccountException 用户不存在
        Account account = accountService.getAccount(token.getUsername());
        return new SimpleAuthenticationInfo("",account.getPassword(),"");
    }
}
