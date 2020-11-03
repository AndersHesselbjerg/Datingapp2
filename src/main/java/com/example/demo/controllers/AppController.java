package com.example.demo.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class AppController {
    private static String path = "classpath*:dependency_config.xml";
    private static final ApplicationContext ctx = new ClassPathXmlApplicationContext(path);
}
