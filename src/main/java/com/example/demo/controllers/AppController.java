package com.example.demo.controllers;

import com.example.demo.data.jdbc.JDBCHandler;
import com.example.demo.domain.controller.BackController;
import com.example.demo.domain.user.UserHandler;
import com.example.demo.domain.user.UserList;
import com.example.demo.models.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @GetMapping("/users")
    public String users (Model model){
        UserList users = backController.getAllUsers();
        model.addAttribute("users", users );
        return "users";
    }


    // Responds to /profile?id=userid
    @RequestMapping(value="profile", method = {RequestMethod.GET, RequestMethod.POST})
    public String profile(@RequestParam int id, Model model) {
        String profileName = backController.getUserByID(id).getUserName();
        String profileTags = backController.getUserByID(id).getTags();
        String profileDesc = backController.getUserByID(id).getDescription();
        model.addAttribute("profileName", profileName);
        model.addAttribute("profileDesc", profileDesc);
        model.addAttribute("profileTags", profileTags);
        return "profile";
    }
}
