package com.howtodoinjava.demo.registration.emailVerification;

import com.howtodoinjava.demo.registration.user.CustomUser;
import com.howtodoinjava.demo.registration.user.CustomUserDetails;
import com.howtodoinjava.demo.registration.user.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class VerificationController {
    @Autowired JdbcUserDetailsManager jdbcUserDetailsManager;
    private CustomUserRepository customUserRepo;

    public VerificationController(CustomUserRepository customUserRepo){
        this.customUserRepo = customUserRepo;
    }

    @GetMapping("/verify/email")
    public String verifyEmail(@RequestParam String eid){
        String encryptedId = eid;
        String username = customUserRepo.findByEncryptedId(encryptedId).getUsername();
        if(jdbcUserDetailsManager.userExists(username)){
            CustomUserDetails customUser = (CustomUserDetails) jdbcUserDetailsManager.loadUserByUsername(username);
            customUser.getUser().setEnabled(true);
            customUserRepo.save(customUser.getUser());
            return "User Verified!";
        }
        return "User invalid.";
    }
}
