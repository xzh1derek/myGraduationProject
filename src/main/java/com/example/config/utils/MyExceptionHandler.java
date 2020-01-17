package com.example.config.utils;
import com.example.controller.ModuleController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackageClasses = ModuleController.class)
public class MyExceptionHandler
{
    @ExceptionHandler(value = org.springframework.http.converter.HttpMessageNotReadableException.class)
    @ResponseBody
    public String exceptionHandler(Exception e)
    {
        return "日期数据格式错误";
    }
}
