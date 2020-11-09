package com.example.demo.presentation;

import com.example.demo.domain.UserList;
import com.example.demo.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class AppController {
    private static String path = "bean_config.xml";
    private static final ApplicationContext ctx = new ClassPathXmlApplicationContext(path);
    private final UserController userController;
    private final ChatController chatController;

    AppController() {
        userController = (UserController) ctx.getBean("userController");
        chatController = (ChatController) ctx.getBean("chatController");
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
        UserList users = userController.getAllUsers();
        model.addAttribute("users", users );
        return "users";
    }

    @RequestMapping(value="/profile")
    public String profile(Model model, Principal principal) {
        String userName = principal.getName();
        User user = userController.getUser(userName);
        model.addAttribute("user", user);
        return "profile";
    }

}
