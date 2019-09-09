package io.levvel.app.passwordReset;

import io.levvel.app.passwordReset.dto.PasswordResetToken;
import io.levvel.app.passwordReset.emailPasswordResetVerification.UserPasswordResetEvent;
import io.levvel.app.registration.user.CustomUserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Controller
@RestController
public class PasswordResetController {
    private final JdbcUserDetailsManager jdbcUserDetailsManager;
    private final PasswordResetService passwordResetService;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final CustomUserRepository customUserRepository;

    public PasswordResetController(JdbcUserDetailsManager jdbcUserDetailsManager, PasswordResetService passwordResetService, ApplicationEventPublisher eventPublisher, CustomUserRepository customUserRepository) {
        this.jdbcUserDetailsManager = jdbcUserDetailsManager;
        this.passwordResetService = passwordResetService;
        this.eventPublisher = eventPublisher;
        this.customUserRepository = customUserRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping(value="/user/reset_password/token", consumes = "application/json")
    public ResponseEntity getResetPasswordToken (@RequestBody Map<String, String> body) throws IOException {
        if (jdbcUserDetailsManager.userExists(body.get("username"))){
            PasswordResetToken token = passwordResetService.createPasswordResetToken(body.get("username"));
            String msg = "http://localhost:8080/user/reset_password/redirect?token=" + token.getToken();
            eventPublisher.publishEvent(new UserPasswordResetEvent(customUserRepository.findByUsername(body.get("username")), token));
            return ResponseEntity.ok().body(token.getToken());
        }
        return ResponseEntity.badRequest().body("User doesn't exist.");
    }

    @PostMapping(value="user/reset_password/reset")
    public ResponseEntity resetPassword (@RequestBody Map<String, String> body) {
        PasswordResetToken token = passwordResetService.getPasswordResetToken(body.get("token"));
        if(passwordResetService.validatePasswordResetToken(token.getToken()) == null) {
            UserDetails currentUser = jdbcUserDetailsManager.loadUserByUsername(token.getUsername());
            String newPassword = body.get("newPassword");
            jdbcUserDetailsManager.changePassword(currentUser.getPassword(), passwordEncoder.encode(newPassword));
            return ResponseEntity.ok().body("Password has been successfully reset.");
        }
        return ResponseEntity.badRequest().body("Invalid token");
    }
}
