package com.edu.nju.joshua.wedesign.service;

import com.edu.nju.joshua.wedesign.mapper.UserMapper;
import com.edu.nju.joshua.wedesign.model.User;
import com.edu.nju.joshua.wedesign.utils.Enums.ChangeInfoResult;
import com.edu.nju.joshua.wedesign.utils.Enums.LoginResult;
import com.edu.nju.joshua.wedesign.utils.Enums.RegisterResult;
import com.edu.nju.joshua.wedesign.utils.login.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EmailService emailService;

    public RegisterResult register(User user) {
        User userExists = userMapper.findByEmail(user.getEmail());

        // In case that one's account is registered by someone unknown
        if (userExists != null) {
            return RegisterResult.ALREADY_REGISTERED;
        }

        Zxcvbn passwordCheck = new Zxcvbn();
        Strength strength = passwordCheck.measure(user.getPassword());

        if (strength.getScore() < 3) {
            return RegisterResult.PASSWORD_TOO_WEAK;
        } else {
            // Set bCrpyted Password to improve security
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setConfirmationToken(UUID.randomUUID().toString());
            userMapper.insertUser(user);
            emailService.sendConfirmationEmail(user);
            return RegisterResult.SUCCESS;
        }
    }
    public LoginResult login(String username, String rawPassword) {
        User user = userMapper.getUserByUsername(username);
        if (user == null)
            return LoginResult.NO_SUCH_ACCOUNT;
        boolean match = bCryptPasswordEncoder.matches(rawPassword, user.getPassword());

        return LoginUtil.getLoginResult(match, user.isEnabled());

    }
    public boolean confirm(String confirmationToken, HttpSession session) {
        User user = userMapper.findByConfirmationToken(confirmationToken);
        if(user == null) {
            return false;
        } else {
            user.setEnabled(true);
            userMapper.updateUser(user);

            session.setAttribute("user", user);
            // TODO Set different roles to different user types.
            session.setAttribute("role", "USER");
            return true;
        }
    }
//    public ChangeInfoResult changeInfo(User user) {
//
//        Zxcvbn passwordCheck = new Zxcvbn();
//        Strength strength = passwordCheck.measure(user.getPassword());
//
//        if (strength.getScore() < 3) {
//            return ChangeInfoResult.PASSWORD_TOO_WEAK;
//        } else {
//            // Set bCrpyted Password to improve security
//            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//            userMapper.updateUser(user);
//            return ChangeInfoResult.SUCCESS;
//        }
//    }

    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }
}
