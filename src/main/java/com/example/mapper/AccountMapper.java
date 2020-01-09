package com.example.mapper;

import com.example.domain.Account;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper
{
    @Update("update account set password=#{p} where username=#{u}")
    void updatePassword(String u, String p);

    @Insert("insert into account(username,password,identity) values(#{username},#{password},#{identity})")
    void createAccount(Account account);

    @Delete("delete from account where username=#{username}")
    void deleteAccount(String username);
}
