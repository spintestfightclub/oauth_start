package io.levvel.app.registration.emailVerification;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailVerificationListener implements ApplicationListener<UserRegistrationEvent> {
    // private final JavaMailSender mailSender;
    @Override
    public void onApplicationEvent(UserRegistrationEvent event){
        String username = event.getUser().getUsername();
        String encryptedId = event.getUser().getEncryptedId();
        String email = event.getUser().getEmail();
        String verLink = "http://localhost:8080/verify/email?eid=" + encryptedId;

        // WRITE MESSAGE TO A FILE FOR NOW, USE THIS EMAIL SENDING LATER
        /*
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("BOTW User Account Verification for " + username);
        message.setText("Account activation link: https://localhost:8080/verify/email?id=");
        message.setTo(email);
        mailSender.send(message);
        */
    }
}
