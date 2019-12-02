package com.example;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer
{
    @Override
    public SpringApplicationBuilder configure(SpringApplicationBuilder builder)
    {
        return builder.sources(DemoApplication.class);
    }
}
