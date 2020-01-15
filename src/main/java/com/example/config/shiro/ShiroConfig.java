package com.example.config.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig
{
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean()
    {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager());
        /**设置shiro内置过滤器
         *  常用过滤器：
         *      anon:无需认证（登录）都能访问
         *      authc：必须登录才能访问
         *      user：如果使用rememberMe功能可以直接访问
         *      perms：该资源必须得到资源权限才可访问
         *      role：该资源必须得到角色权限才可访问
         */
        Map<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/loginShiro","anon");
        //测试时别的功能时注释掉，测试权限时才开启。开启后只能在POSTMAN上试，在浏览器上登录了也白登
        //filterMap.put("/userInfo","perms[whatTheFuck]");
        //filterMap.put("/*","authc");
        shiroFilterFactoryBean.setLoginUrl("/index");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager()
    {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(getRealm());
        return defaultWebSecurityManager;
    }

    @Bean
    public UserRealm getRealm()
    {
        return new UserRealm();
    }
}
