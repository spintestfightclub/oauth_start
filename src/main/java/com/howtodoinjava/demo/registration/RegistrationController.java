package com.howtodoinjava.demo.registration;

import com.howtodoinjava.demo.registration.emailVerification.UserRegistrationEvent;
import com.howtodoinjava.demo.registration.user.*;
import com.howtodoinjava.demo.utils.StringEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RestController
public class RegistrationController {
    @Autowired private JdbcUserDetailsManager jdbcUserDetailsManager;
    @Autowired private ApplicationEventPublisher eventPublisher;

    private CustomUserRepository customUserRepo;
    private CustomAuthoritiesRepository customAuthoritiesRepository;
    private final PasswordEncoder passwordEncoder;
    private final StringEncoder idEncoder;

    public RegistrationController(BCryptPasswordEncoder passwordEncoder, CustomUserRepository customUserRepo, StringEncoder idEncoder, CustomAuthoritiesRepository customAuthoritiesRepository){
        this.passwordEncoder = passwordEncoder;
        this.customUserRepo = customUserRepo;
        this.idEncoder = idEncoder;
        this.customAuthoritiesRepository = customAuthoritiesRepository;
    }

    @PostMapping(value = "/user/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity register(@RequestBody Map<String, String> body) throws Exception{
        //TODO: check for valid inputs in json body (ValidEmail; confirmed password matches, confirm not empty), maybe use automated data binding
        List<String> tempRoles = Arrays.asList(body.get("authorities").split(","));
        String username = body.get("username");
        String password = passwordEncoder.encode(body.get("password"));
        String email = body.get("email");
        GrantedAuthority gAuth = new SimpleGrantedAuthority(body.get("authorities"));
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : tempRoles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        try{
            CustomUser customUser = CustomUser.builder().username(username).password(password).email(email).enabled(false).authority(gAuth).encryptedId(idEncoder.encode(username + password)).accountNonExpired(true).accountNonLocked(true).credentialsNonExpired(true).totpEnabled(true).build();
            customUserRepo.saveIfNotExists(customUser);
            CustomAuthorities customAuthority = CustomAuthorities.builder().username(username).authority(gAuth).build();
            customAuthoritiesRepository.save(customAuthority);

            eventPublisher.publishEvent(new UserRegistrationEvent(customUser));
            return ResponseEntity.ok().body(body);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Could not register user because: " + e.getMessage());
        }
    }
}
