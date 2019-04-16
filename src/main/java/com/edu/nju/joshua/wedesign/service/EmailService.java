package com.edu.nju.joshua.wedesign.service;

import com.edu.nju.joshua.wedesign.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service("emailService")
public class EmailService {
    @Value("${app.host.url}")
    private String hostUrl;
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;

    // The calling code doesn't have to wait for the send operation to complete in order to continue
    @Async
    public void sendConfirmationEmail(User user) {
        SimpleMailMessage registrationEmail = new SimpleMailMessage();

        String confirmationUrl = hostUrl + "confirmUser?token=" + user.getConfirmationToken();
        // Configure mail parameters.
        registrationEmail.setFrom(from);
        registrationEmail.setTo(user.getEmail());
        registrationEmail.setSubject("Online Education Registration Confirmation");
        registrationEmail.setText("Hi, " + user.getName() + "! Thank you for your choice of our service." +
                "To confirm your e-mail address, please click the link below:\n" + confirmationUrl);

        mailSender.send(registrationEmail);
    }
}
