package com.howtodoinjava.demo.registration.totp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class TOTPController {
    private final TOTPQueryService totpService;

   /* @PostMapping("/confirm-totp")
    public String confirmGoogleAuthenticatorSetup(Model model, Principal principal, @ModelAttribute("codeDto") TotpCode codeDto){
        boolean userHasTotpEnabled = totpService.isTotpEnabled(principal.getName());
        if(!userHasTotpEnabled){
            totpService.enableTOTPForUser(principal.getName(), Integer.valueOf(codeDto.getCode()));
            model.addAttribute("totpEnabled", true);
        };
    }

    @GetMapping("/authenticator-url")
    public String getGoogleAuthenticatorQRUrl(Model model, Principal principal){
        boolean userHasTotpEnabled = totpService.isTotpEnabled(principal.getName());
        if(!userHasTotpEnabled){
            model.addAttribute("qrUrl", totpService.generateNewGoogleAuthQrUrl(principal.getName()));
        }
        model.addAttribute("totpEnabled", userHasTotpEnabled);
        return "account";
    }*/
}
