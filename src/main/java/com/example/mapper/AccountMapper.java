package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper
{
    @Update("update account set password=#{p} where username=#{u}")
    void updatePassword(String u, String p);
}
