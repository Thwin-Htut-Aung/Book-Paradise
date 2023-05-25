package com.book.paradise.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.book.paradise.entity.PasswordResetToken;
import com.book.paradise.entity.Users;
import com.book.paradise.repo.PasswordResetTokenRepo;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

@Service
@Transactional
public class PasswordResetService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenRepo tokenRepo;

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
	private PasswordEncoder passwordEncoder;

    public boolean sendPasswordResetEmail(String email, int resetCode) throws MessagingException {
        // Check if the email address is registered
        Users user = userService.findByEmail(email);
        if (user == null) {
        	return false; 
        }
        
        else{
        // Create a password reset token and save it to the database
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
        tokenRepo.save(passwordResetToken);

        // Send a password reset email to the user
      //  String resetLink = "http://localhost:8080/reset-password?token=" + token;
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Password Reset Request");
        helper.setText("Hello " + user.getFullName() + ",\n\n"
                + "We are aware that you have forgotton your Sartoon password. "
                + "Please use the following reset code to reset your password.\n"
                + resetCode + "\n\n"
                + "If you did not request a password reset, please ignore this email.\n\n"
                + "Thanks,\n Sartoon Team");
        mailSender.send(message);
        return true;
        }
    }

    public void resetPassword(String email, String newPassword) {
        // Look up the password reset token in the database
      /*  PasswordResetToken passwordResetToken = tokenRepo.findByResetToken(token);
        if (passwordResetToken == null) {
            throw new IllegalArgumentException("Invalid password reset token: " + token);
        }

        // Check if the token has expired
        if (passwordResetToken.isExpired()) {
            throw new IllegalArgumentException("Password reset token has expired: " + token);
            }*/
        

        // Update the user's password and save the user to the database
        Users user = userService.findByEmail(email);
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.saveUser(user);

        // Delete the password reset token from the database
       // tokenRepo.delete(passwordResetToken);
    }
}

