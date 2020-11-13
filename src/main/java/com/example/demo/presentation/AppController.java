package com.example.demo.presentation;

import com.example.demo.domain.*;
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
    private final CandidateController candidateController; //gider ikke være final?


    AppController() {
        userController = (UserController) ctx.getBean("userController");
        chatController = (ChatController) ctx.getBean("chatController");
        loginController = (LoginController) ctx.getBean("loginController");
        candidateController = (CandidateController) ctx.getBean("candidateController");
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
    public String users(WebRequest request, Model model) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        UserList users = userController.getAllUsers();
        model.addAttribute("users", users);
        if (user != null ) {
        return "users";
        }
        else
            return "redirect:/";
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
        model.addAttribute("profileId", user.getUserid());
        return "profile";
    }

    @RequestMapping(value = "chat", method = {RequestMethod.GET, RequestMethod.POST})
    public String chat(@RequestParam int id, WebRequest request, Model model) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        ChatList chatlist = chatController.getChats(user);
        Chat chat = chatlist.getById(id);
        model.addAttribute("users", chatController.getUsers(id));
        model.addAttribute("chatid", id);
        model.addAttribute("chat", chat.getMessages());
        return "chat";
    }

    @PostMapping("/sendmsg")
    public String sendmsg(@RequestParam String msg, @RequestParam int id, WebRequest request, Model model) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        ChatList chatlist = chatController.getChats(user);
        Chat chat = chatlist.getById(id);
        chatController.sendMessage(chat,user,msg);
        ChatList new_chatlist = chatController.getChats(user);
        Chat new_chat = new_chatlist.getById(id);
        model.addAttribute("users", chatController.getUsers(id));
        model.addAttribute("chatid", id);
        model.addAttribute("chat", new_chat.getMessages());
        return "chat";
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
        String email = request.getParameter("mail");
        String pwd = request.getParameter("password");
        User user = loginController.login(email, pwd);
        setSessionInfo(request, user);
        return "loggedin";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @GetMapping("/loggedin")
    public String loggedin(WebRequest request, Model model) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        model.addAttribute("candidates", candidateController.getCandidatesOfUserID(user.getUserid(),1,20));
        model.addAttribute("users", userController.getAllUsers());
        if (user != null) {
            return "loggedin";
        } else
            return "redirect:/";
    }

    @GetMapping("/editprofile")
    public String editprofile(WebRequest request) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        if (user != null) {
            return "editprofile";
        } else
            return "redirect:/";
    }

    @RequestMapping(value = "/updateprofile", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateprofile(@RequestParam String userName,
                                @RequestParam String tags,
                                @RequestParam String description,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String phone,
                                @RequestParam String mail,
                                WebRequest request) throws LoginException {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        user.setUserName(userName);
        user.setTags(tags);
        user.setDescription(description);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setMail(mail);
        user.setPassword(password2);

        userController.updateUser(user);

        if (password1.equals(password2)) {
            return "loggedin";

        } else throw new LoginException("The two passwords did not match");

    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam int id) {
        User user = userController.getUserById(id);
        userController.deleteUser(user);
        return "success";
    }

    @PostMapping("/addcandidate")
    public String registerUser(
            @RequestParam int user_id,
            WebRequest request) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        Candidate candidate = new Candidate(user_id, user.getUserid());
        candidateController.InsertCandidate(candidate);
        return "success";
    }

    /*
    * Mangler opret ny chat.
    * Mangler billed upload.
    * Mangler logout*
    * Mangler Kandidat liste.
    * Design
    * */

}
