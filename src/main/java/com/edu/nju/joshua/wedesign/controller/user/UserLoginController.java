package com.edu.nju.joshua.wedesign.controller.user;

import com.edu.nju.joshua.wedesign.model.User;
import com.edu.nju.joshua.wedesign.mapper.UserMapper;
import com.edu.nju.joshua.wedesign.service.UserService;
import com.edu.nju.joshua.wedesign.utils.Enums.LoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UserLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    User user;
    @Autowired
    UserMapper userMapper;
    @RequestMapping(value = {"/login/user","/"}, method = RequestMethod.GET)
    public String showUserLoginPage(Model model, @ModelAttribute(value = "user") User user) {
        return "user/login";
    }
    @RequestMapping(value = "/login/user", method = RequestMethod.POST)
    public String processUserLoginForm(Model model, @ModelAttribute(value = "user") User user, HttpSession session, RedirectAttributes redir) throws Exception {
        LoginResult loginResult = userService.login(user.getUsername(), user.getPassword());

        if (loginResult == LoginResult.NO_SUCH_ACCOUNT) {
            model.addAttribute("normalErrorMessage", "There is no such account! Please register first.");
            return "user/login";
        } else if (loginResult == LoginResult.WRONG_PASSWORD) {
            model.addAttribute("normalErrorMessage", "Wrong password!");
            return "user/login";
        } else if (loginResult == LoginResult.NOT_ACTIVATED) {
            model.addAttribute("normalErrorMessage", "Please activate your account first!");
            return "user/login";
        } else if (loginResult == LoginResult.SUCCESS) {
            User currentUser = userService.getUserByUsername(user.getUsername());
            session.setAttribute("user", currentUser);
            session.setAttribute("role", "USER");
            model.addAttribute("user",currentUser);
            return "redirect:/space";
        } else {
            throw new Exception("FATAL ERROR! LOGIN LOGIC INCOMPLETE!");
        }
    }
}
