package com.howtodoinjava.demo.passwordReset.emailPasswordResetVerification;

import com.howtodoinjava.demo.passwordReset.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class PasswordResetVerificationController {
    private PasswordResetService passwordResetService;

    public PasswordResetVerificationController(PasswordResetService passwordResetService){
        this.passwordResetService = passwordResetService;
    }

    @GetMapping(value="/user/reset_password/redirect")
    public ResponseEntity passwordResetVerifyEmail(@RequestParam String token){
        if(passwordResetService.validatePasswordResetToken(token) == null){
            return ResponseEntity.ok().body("Redirected to the password reset page.");
        }
        return ResponseEntity.badRequest().body("Invalid token.");
    }
}
