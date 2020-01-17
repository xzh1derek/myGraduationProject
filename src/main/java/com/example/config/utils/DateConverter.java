package com.example.config.utils;
import java.text.SimpleDateFormat;

public class DateConverter
{
    static public java.sql.Date convert(String source)
    {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = df.parse(source);
            return new java.sql.Date(date.getTime());
        } catch (Exception e) {
            throw new RuntimeException("日期输入错误");
        }
    }
}
