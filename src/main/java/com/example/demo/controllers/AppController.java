package com.example.demo.controllers;

import com.example.demo.domain.JDBC.JDBCHandler;
import com.example.demo.domain.User.UserHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    private static String path = "bean_config.xml";
    private static final ApplicationContext ctx = new ClassPathXmlApplicationContext(path);
    private final JDBCHandler jdbcHandler;
    private final UserHandler userHandler;

    AppController() {
        jdbcHandler = (JDBCHandler) ctx.getBean("jdbcHandler");
        jdbcHandler.setConnection();
        userHandler = (UserHandler) ctx.getBean("userHandler");
    }

    @GetMapping("/")
    public String index (){
        return "index";
    }

    @GetMapping("/error")
    public String error (){
        return "error";
    }

    @GetMapping("/user")
    public String user (){
        return "user";
    }

    @GetMapping("/userlist")
    public String userlist (){
        return "userlist";
    }

}
