package io.levvel.app.passwordReset.emailPasswordResetVerification;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailPasswordResetVerificationListener implements ApplicationListener<UserPasswordResetEvent> {
    // private final JavaMailSender mailSender;
    @Override
    public void onApplicationEvent(UserPasswordResetEvent event){
        String username = event.getUser().getUsername();
        String email = event.getUser().getEmail();
        String token = event.getToken().getToken();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("emailPasswordReset.txt"));
            writer.write("Email: " + email + "\nVerification Link: http://localhost:8080/user/reset_password/redirect?token=" + token);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
