package com.example.config.utils;

import javax.persistence.Converter;
import java.lang.annotation.Annotation;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter
{
    static public Date convert(String source)
    {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.parse(source);
        } catch (Exception e) {
            throw new RuntimeException("日期输入错误");
        }
    }
}
