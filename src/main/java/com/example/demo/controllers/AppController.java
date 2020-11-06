package com.example.demo.controllers;

import com.example.demo.data.jdbc.JDBCHandler;
import com.example.demo.domain.controller.BackController;
import com.example.demo.domain.user.UserHandler;
import com.example.demo.domain.user.UserList;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.ResultSet;

@Controller
public class AppController {
    private static String path = "bean_config.xml";
    private static final ApplicationContext ctx = new ClassPathXmlApplicationContext(path);
    private final BackController backController;

    AppController() {
        backController = (BackController) ctx.getBean("backController");
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
        UserList users = backController.getAllUsers();
        model.addAttribute("users", users );
        return "users";
    }
}