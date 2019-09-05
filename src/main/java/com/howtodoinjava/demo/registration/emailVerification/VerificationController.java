package com.howtodoinjava.demo.registration.emailVerification;

import com.howtodoinjava.demo.registration.user.CustomUser;
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
        CustomUser custUser = customUserRepo.findByEncryptedId(encryptedId);
        if(jdbcUserDetailsManager.userExists(custUser.getUsername())){
            UserDetails defUser = jdbcUserDetailsManager.loadUserByUsername(custUser.getUsername());
            custUser.setEnabled(true);
            jdbcUserDetailsManager.updateUser(new User(defUser.getUsername(), defUser.getPassword(), true, defUser.isAccountNonExpired(), defUser.isCredentialsNonExpired(), defUser.isAccountNonLocked(), defUser.getAuthorities()));
            return "User Verified!";
        }
        return "User invalid.";
    }
}
