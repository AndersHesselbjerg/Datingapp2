package com.example.demo.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class AppController {
    private static String path = "C:\\Users\\patri\\IdeaProjects\\Sem02\\DatingApp_MiniProject\\src\\main\\java\\com\\example\\demo\\dependency_config.xml";
    private static final ApplicationContext ctx = new FileSystemXmlApplicationContext(path);
}
