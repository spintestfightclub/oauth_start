package io.levvel.app.passwordReset;

import io.levvel.app.passwordReset.dto.PasswordResetToken;
import io.levvel.app.passwordReset.emailPasswordResetVerification.UserPasswordResetEvent;
import io.levvel.app.registration.user.CustomUser;
import io.levvel.app.registration.user.CustomUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Controller
@RestController
@AllArgsConstructor
public class PasswordResetController {
    private final String URL_REDIRECT_PREFIX = "http://localhost:8080/user/reset_password/redirect?token=";

    private final PasswordResetService passwordResetService;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final CustomUserRepository customUserRepository;

    @PostMapping(value="/user/reset_password/token", consumes = "application/json")
    public ResponseEntity getResetPasswordToken (@RequestBody Map<String, String> body) throws IOException {
        String username = body.get("username");
        CustomUser customUser = customUserRepository.findByUsername(username);
        if (customUser != null){
            PasswordResetToken token = passwordResetService.createPasswordResetToken(username);
            String msg = URL_REDIRECT_PREFIX + token.getToken();
            eventPublisher.publishEvent(new UserPasswordResetEvent(customUser, token));
            return ResponseEntity.ok().body(msg);
        }
        return ResponseEntity.badRequest().body("User doesn't exist.");
    }

    @PostMapping(value="user/reset_password/reset")
    public ResponseEntity resetPassword (@RequestBody Map<String, String> body) {
        PasswordResetToken resetToken = passwordResetService.getPasswordResetToken(body.get("token"));
        if(passwordResetService.validatePasswordResetToken(resetToken.getToken()) == null) {
            CustomUser customUser = customUserRepository.findByUsername(resetToken.getUsername());
            String newPassword = body.get("newPassword");
            customUser.setPassword(passwordEncoder.encode(newPassword));
            customUserRepository.save(customUser);
            return ResponseEntity.ok().body("Password has been successfully reset.");
        }
        return ResponseEntity.badRequest().body("Invalid token");
    }
}
