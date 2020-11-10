package com.example.demo.presentation;

import com.example.demo.domain.UserList;
import com.example.demo.domain.User;
import com.mysql.cj.log.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.security.auth.login.LoginException;
import java.security.Principal;

@Controller
public class AppController {
    private static String path = "bean_config.xml";
    private static final ApplicationContext ctx = new ClassPathXmlApplicationContext(path);
    private final UserController userController;
    private final ChatController chatController;
    private final LoginController loginController;


    AppController() {
        userController = (UserController) ctx.getBean("userController");
        chatController = (ChatController) ctx.getBean("chatController");
        loginController = (LoginController) ctx.getBean("loginController");
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/users")
    public String users(Model model) {
        UserList users = userController.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String userName,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String mail) {
        User user = new User (0,
                userName,
                password,
                lastName,
                firstName,
                null,
                null,
                mail,
                null,
                null,
                null ,
                0);
        userController.insertUser(user);
        return "success";
    }



    // Responds to /profile?id=userid
    @RequestMapping(value = "profile", method = {RequestMethod.GET, RequestMethod.POST})
    public String profile(@RequestParam int id, Model model) {
        User user = userController.getUserById(id);
        model.addAttribute("profileName", user.getUserName());
        model.addAttribute("profileDesc", user.getDescription());
        model.addAttribute("profileTags", user.getTags());
        return "profile";
    }

}

    @PostMapping("/login")
    public String loginUser(WebRequest request) {
        //Retrieve values from HTML form via WebRequest
        String email = request.getParameter("email");
        String pwd = request.getParameter("password");
        try {
            User user = loginController.login(email, pwd);
            setSessionInfo(request, user);
        } catch (LoginException e) {
            e.getStackTrace();
        }

    }
}
