package com.example.demo.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    private static String path = "classpath*:dependency_config.xml";
    private static final ApplicationContext ctx = new ClassPathXmlApplicationContext(path);

    @GetMapping("/error")
    public String error (){
        return "error";
    }
}
