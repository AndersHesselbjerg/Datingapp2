package com.example.demo.presentation;

import com.example.demo.domain.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.LoginException;

@Controller
public class AppController {
    private static String path = "bean_config.xml";
    private static final ApplicationContext ctx = new ClassPathXmlApplicationContext(path);
    private final UserController userController;
    private final ChatController chatController;
    private final LoginController loginController;
    private final CandidateController candidateController; //gider ikke være final?
    private final FileUploadController fileUploadController;

    AppController() {
        userController = (UserController) ctx.getBean("userController");
        chatController = (ChatController) ctx.getBean("chatController");
        loginController = (LoginController) ctx.getBean("loginController");
        candidateController = (CandidateController) ctx.getBean("candidateController");
        fileUploadController = (FileUploadController) ctx.getBean("fileUploadController");
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
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
                "user",
                null);

        userController.insertUser(user);
        return "success";
    }

    @PostMapping("/login")
    public String loginUser(WebRequest request, Model model) throws LoginException {
        String email = request.getParameter("mail");
        String pwd = request.getParameter("password");
        User user = loginController.login(email, pwd);
        setSessionInfo(request, user);
        CandidateList candidateList = candidateController.getCandidatesOfUserID(user.getUserid(), 1, 20);
        UserList userList = new UserList();
        for (Candidate ca : candidateList) {
            userList.add(userController.getUserById(ca.getUser_id()));
        }
        model.addAttribute("candidates", userList);
        model.addAttribute("users", userController.getAllUsers());
        return "loggedin";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @GetMapping("/loggedin")
    public String loggedin(WebRequest request, Model model) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        CandidateList candidateList = candidateController.getCandidatesOfUserID(user.getUserid(), 1, 20);
        UserList userList = new UserList();
        for (Candidate ca : candidateList) {
            userList.add(userController.getUserById(ca.getUser_id()));
        }
        model.addAttribute("candidates", userList);
        model.addAttribute("users", userController.getAllUsers());
        if (user != null) {
            return "loggedin";
        } else
            return "redirect:/";
    }


    // Responds to /profile?id=userid
    @RequestMapping(value = "profile", method = {RequestMethod.GET, RequestMethod.POST})
    public String profile(@RequestParam int id, Model model) {

        User user = userController.getUserById(id);
        model.addAttribute("profilePic", user.byteArrayAs64String());
        model.addAttribute("profileName", user.getUserName());
        model.addAttribute("profileDesc", user.getDescription());
        model.addAttribute("profileTags", user.getTags());
        model.addAttribute("profileId", user.getUserid());
        return "profile";
    }



    @GetMapping("/users")
    public String users(WebRequest request, Model model) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        UserList users = userController.getAllUsers();
        model.addAttribute("base64", user.byteArrayAs64String());
        model.addAttribute("users", users);
        if (user != null) {
            return "users";
        } else
            return "redirect:/";
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

    @PostMapping("/deleteuser")
    public String deleteUser(@RequestParam int id) {
        User user = userController.getUserById(id);
        userController.deleteUser(user);
        return "success";
    }

    @RequestMapping(value = "/deletecandidate", method = {RequestMethod.GET, RequestMethod.POST})
    public String deletecandidate(@RequestParam int id, WebRequest request) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        CandidateList candidateList = candidateController.getCandidatesOfUserID(user.getUserid(), 1, 20);
        for (Candidate ca : candidateList) {
            if (ca.getUser_id() == id) {
                candidateController.deleteCandidate(ca);
            }
        }
        return "success";
    }

    @PostMapping("/addcandidate")
    public String registerUser(
            @RequestParam int user_id,
            WebRequest request) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        Candidate candidate = new Candidate(user_id, user.getUserid());
        candidateController.InsertCandidate(candidate);
        return "loggedin";
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

    @RequestMapping(value = "chat", method = {RequestMethod.GET, RequestMethod.POST})
    public String chat(@RequestParam int id, WebRequest request, Model model) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        ChatList chatlist = chatController.getChats(user);
        Chat chat = chatlist.getById(id);
        model.addAttribute("names", chat.getNames());
        model.addAttribute("users", chatController.getUsers(id));
        model.addAttribute("chatid", id);
        model.addAttribute("chat", chat.getMessages());
        return "chat";
    }

    @PostMapping("/newchat")
    public String newchat(
            @RequestParam int user_id, WebRequest request) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        chatController.createChat(user.getUserid(),user_id);
        return "mychats";
    }

    @PostMapping("/sendmsg")
    public String sendmsg(@RequestParam String msg, @RequestParam int id, WebRequest request, Model model) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        ChatList chatlist = chatController.getChats(user);
        Chat chat = chatlist.getById(id);
        chatController.sendMessage(chat, user, msg);
        ChatList new_chatlist = chatController.getChats(user);
        Chat new_chat = new_chatlist.getById(id);
        model.addAttribute("users", chatController.getUsers(id));
        model.addAttribute("chatid", id);
        model.addAttribute("chat", new_chat.getMessages());
        return "chat";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, WebRequest request) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        userController.addProfilePicture(user.getUserid(), file);
        return "success";
    }

    @PostMapping("/uploadimg")
    public String uploadimg(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
        modelMap.addAttribute("file", file);
        fileUploadController.uploadAsPath(file);
        //return "uploadResult";
        //modelMap("files", fh.getAllImages());
        return "redirect:/";
    }

    /*
     * Mangler opret ny chat.
     * Mangler billed upload.
     * Mangler logout*
     * Design
     * */

}
