package com.example.demo.presentation;

import com.example.demo.domain.UserList;
import com.example.demo.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
                0);
        userController.insertUser(user);
        return "success";
    }



    @GetMapping("/admin")
    public String admin(Model model) {
        UserList users = userController.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
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

    // Responds to /editprofile?id=userid
    @RequestMapping(value = "editprofile", method = {RequestMethod.GET, RequestMethod.POST})
    public String editprofile(@RequestParam int id, Model model) {
        User user = userController.getUserById(id);
        model.addAttribute("profileName", user.getUserName());
        model.addAttribute("profileDesc", user.getDescription());
        model.addAttribute("profileTags", user.getTags());
        model.addAttribute("profileFirstName", user.getFirstName());
        model.addAttribute("profileLastName", user.getLastName());
        model.addAttribute("profilePhone", user.getPhone());
        model.addAttribute("profileMail", user.getMail());
        model.addAttribute("profileTimeOfRegistry", user.getTimeOfRegistry());
        model.addAttribute("profileCreditInfo", user.getCreditInfo());
        return "editprofile";
    }

    @RequestMapping(value = "update", method = {RequestMethod.PUT, RequestMethod.GET})
    public String update(
            @RequestParam int id,
            @RequestParam String userName,
            @RequestParam String tags,
            @RequestParam String description,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String phone,
            @RequestParam String mail) {
        User user = userController.getUserById(id);
        user.setUserName(userName);
        user.setTags(tags);
        user.setDescription(description);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setMail(mail);
        userController.updateUser(user);
        return "update";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam int id){
        User user = userController.getUserById(id);
        userController.deleteUser(user);
        return "success";
    }
}
