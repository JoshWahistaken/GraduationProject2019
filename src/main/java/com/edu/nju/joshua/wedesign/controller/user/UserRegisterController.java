package com.edu.nju.joshua.wedesign.controller.user;

import com.edu.nju.joshua.wedesign.mapper.UserMapper;
import com.edu.nju.joshua.wedesign.model.User;
import com.edu.nju.joshua.wedesign.service.UserService;
import com.edu.nju.joshua.wedesign.utils.Enums.RegisterResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UserRegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    User user;
    @Autowired
    UserMapper userMapper;
    @RequestMapping(value = {"/register/user","/register"}, method = RequestMethod.GET)
    public String showUserRegistrationPage(Model model, @ModelAttribute(value = "user") User user) {
        return "user/register";
    }

    @RequestMapping(value = "/register/user", method = RequestMethod.POST)
    public String processUserRegisterationForm(Model model, @ModelAttribute(value = "user") User user, HttpServletRequest request, RedirectAttributes redir) throws Exception {
        RegisterResult result = userService.register(user);
        if (result == RegisterResult.ALREADY_REGISTERED) {
            model.addAttribute("alreadyRegisteredMessage", "Oops!  There is already a user registered with the email provided.");
            return "user/register";
        } else if (result == RegisterResult.PASSWORD_TOO_WEAK) {
            // Flash attributes only exist for one redirect.
            model.addAttribute("normalErrorMessage", "Your password is too weak.  Choose a stronger one.");
            return "user/register";
        } else if (result == RegisterResult.SUCCESS) {
            model.addAttribute("confirmationMessage", "A confirmation e-mail has been sent to " + user.getEmail());
            return "user/register";
        } else {
            throw new Exception("FATAL ERROR! REGISTER LOGIC INCOMPLETE!");
        }

    }
    @RequestMapping(value = "/confirmUser", method = RequestMethod.GET)
    public String confirmRegistration(Model model, @RequestParam("token") String token, RedirectAttributes redir, HttpSession session) {
        if (userService.confirm(token, session)) { // No token found in DB
            redir.addFlashAttribute("successMessage", "Your account has been activated!");
            return "redirect:/user";
        } else {
            redir.addFlashAttribute("normalErrorMessage", "Oops!  This is an invalid confirmation link. Please register an accnout instead.");
            return "redirect:/register/user";

        }
    }
}
