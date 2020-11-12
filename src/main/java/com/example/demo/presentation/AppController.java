package com.example.demo.presentation;

import com.example.demo.domain.ChatList;
import com.example.demo.domain.UserList;
import com.example.demo.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.security.auth.login.LoginException;

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

    /*@GetMapping("/users")
    public String users(WebRequest request, Model model) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        UserList users = userController.getAllUsers();
        model.addAttribute("users", users);
        if (user != null ) {
        return "users";
        }
        else
            return "redirect:/";
    }*/

    @GetMapping("/users")           // Test uden authenticqation
    public String users(Model model) {
        UserList users = userController.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/mychats")
    public String chats(WebRequest request, Model model) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        ChatList mychats = chatController.getChats(user);
        model.addAttribute("mychats", mychats);
        if (user != null) {
            return "mychats";
        } else
            return "redirect:/";
    }

    @GetMapping("/admin")           // Test uden authenticqation
    public String admin(Model model) {
        UserList users = userController.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String userName,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String mail) {
        User user = new User(0,
                userName,
                password,
                lastName,
                firstName,
                null,
                null,
                mail,
                null,
                null,
                null,
                0,
                "user");

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

    @GetMapping("/getprofile")
    public String getprofile(WebRequest request, Model model) {
        String userName = request.getParameter("userName");
        User user = userController.getUser(userName);
        return profile(user.getUserid(), model);
    }

    private void setSessionInfo(WebRequest request, User user) {
        // Place user info on session
        request.setAttribute("user", user, WebRequest.SCOPE_SESSION);
        request.setAttribute("role", user.getRole(), WebRequest.SCOPE_SESSION);
    }

    @PostMapping("/login")
    public String loginUser(WebRequest request) throws LoginException {
        //Retrieve values from HTML form via WebRequest
        String email = request.getParameter("mail");
        String pwd = request.getParameter("password");
        User user = loginController.login(email, pwd);
        setSessionInfo(request, user);
        return "loggedin";
    }

    @GetMapping("/loggedin")
    public String loggedin(WebRequest request) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        if (user != null) {
            return "loggedin";
        } else
            return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam int id) {
        User user = userController.getUserById(id);
        userController.deleteUser(user);
        return "success";
    }


}
