package io.levvel.app.registration.totp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

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
