package com.example.studentworkslibrary.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@CrossOrigin
@Configuration
@EnableWebMvc
public class StaticResolver implements WebMvcConfigurer {

    @Value("${path.to.teacher.page}")
    private String teacherPagePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String file = "file:";
        registry.addResourceHandler("/build/**").addResourceLocations(file.concat(teacherPagePath));
    }
}
