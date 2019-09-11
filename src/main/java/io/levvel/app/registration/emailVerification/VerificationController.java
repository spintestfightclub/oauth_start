package io.levvel.app.registration.emailVerification;

import io.levvel.app.registration.user.CustomUser;
import io.levvel.app.registration.user.CustomUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class VerificationController {
    private CustomUserRepository customUserRepo;

    public VerificationController(CustomUserRepository customUserRepo){
        this.customUserRepo = customUserRepo;
    }

    @GetMapping("/verify/email")
    public String verifyEmail(@RequestParam String eid){
        if(customUserRepo.findByEncryptedId(eid) != null) {
            CustomUser customUser = customUserRepo.findByEncryptedId(eid);
            customUser.setEnabled(true);
            customUserRepo.save(customUser);
            return "User Verified!";
        }
        return "User invalid.";
    }
}
