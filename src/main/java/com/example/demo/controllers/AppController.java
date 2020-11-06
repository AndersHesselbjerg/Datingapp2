package com.example.demo.controllers;

import com.example.demo.domain.JDBC.JDBCHandler;
import com.example.demo.domain.User.UserHandler;
import com.example.demo.domain.User.UserList;
import com.example.demo.models.User;
import com.mysql.cj.protocol.Resultset;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.ResultSet;
import java.util.ArrayList;

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

    @GetMapping("/users")
    public String users (Model model){
        ResultSet resultset = jdbcHandler.getAllUsers();
        userHandler.buildUsers(resultset);
        UserList users = userHandler.fetchUsers();
        model.addAttribute("users", users );
        return "users";
    }

}
