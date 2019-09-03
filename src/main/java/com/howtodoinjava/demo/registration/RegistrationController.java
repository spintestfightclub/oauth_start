package com.howtodoinjava.demo.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RestController
public class RegistrationController {
    @Autowired private UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping(value = "/user/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity register(@RequestBody Map<String, String> body) throws Exception{
        //TODO: check for valid inputs in json body (ValidEmail; confirmed password matches, confirm not empty), maybe use automated data binding
        List<String> tempRoles = Arrays.asList(body.get("authorities").split(","));
        String username = body.get("username");
        String password = passwordEncoder.encode(body.get("password"));
        String email = body.get("email");
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : tempRoles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        try{
            // UserDto userDto = new UserDto(username, authorities, email, password, password);
            @Valid
            UserDetails user = new CustomUser(username, password, authorities, email);

            userDetailsManager.createUser(user);
            return ResponseEntity.ok().body(body);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Could not register user because: " + e.getMessage());
        }
    }
}
