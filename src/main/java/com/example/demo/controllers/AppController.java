package com.example.demo.controllers;

import com.example.demo.services.JDBC.JDBCHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AppController {
    private static String path = "bean_config.xml";
    private static final ApplicationContext ctx = new ClassPathXmlApplicationContext(path);
    private final JDBCHandler jdbcHandler;

    AppController() {
        jdbcHandler = (JDBCHandler) ctx.getBean("jdbcHandler");
        jdbcHandler.setConnection();
    }

    @GetMapping("/")
    public String index (){
        return "index";
    }

    @GetMapping("/error")
    public String error (){
        return "error";
    }

    @GetMapping("/userlist")
    public String userlist (){
        return "userlist";
    }

}
