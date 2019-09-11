package io.levvel.app.registration;

import io.levvel.app.registration.emailVerification.UserRegistrationEvent;
import io.levvel.app.registration.totp.TOTPService;
import io.levvel.app.registration.user.CustomAuthorities;
import io.levvel.app.registration.user.CustomAuthoritiesRepository;
import io.levvel.app.registration.user.CustomUser;
import io.levvel.app.registration.user.CustomUserRepository;
import io.levvel.app.utils.StringEncoder;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@Controller
@RestController
@AllArgsConstructor
public class RegistrationController {
    // @Value("${test}")
    private final String AUTHORITY = "USER";

    // @AllArgsConstructor takes care of this for us
    private ApplicationEventPublisher eventPublisher;
    private CustomUserRepository customUserRepository;
    private CustomAuthoritiesRepository customAuthoritiesRepository;
    private PasswordEncoder passwordEncoder;
    private StringEncoder idEncoder;
    private TOTPService totpService;

    @PostMapping(value = "/user/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity register(@RequestBody Map<String, String> body) throws Exception{
        //TODO: check for valid inputs in json body (ValidEmail; confirmed password matches, confirm not empty), maybe use automated data binding
        String username = body.get("username");
        String password = passwordEncoder.encode(body.get("password"));
        String email = body.get("email");
        String qrUrl = "";
        GrantedAuthority grantedAuth = new SimpleGrantedAuthority(AUTHORITY);


        try{
      CustomUser customUser =
          CustomUser.builder()
              .username(username)
              .password(password)
              .email(email)
              .enabled(false)
              .authority(grantedAuth)
              .encryptedId(idEncoder.encode(username + password + new Date().toString()))
              .accountNonExpired(true)
              .accountNonLocked(true)
              .credentialsNonExpired(true)
              .totpEnabled(true)
              .secret(Base32.random())
              .build();
            customUserRepository.save(customUser);
            CustomAuthorities customAuthority = CustomAuthorities.builder().username(username).authority(grantedAuth).build();
            customAuthoritiesRepository.save(customAuthority);

            if(customUser.isTotpEnabled()){
                qrUrl = totpService.generateQRUrl(customUser);
            }

            eventPublisher.publishEvent(new UserRegistrationEvent(customUser));
            body.put("email-verification", "http://localhost:8080/verify/email?eid=" + customUser.getEncryptedId());
            body.put("qr-url", qrUrl);
            return ResponseEntity.ok().body(body);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Could not register user because: " + e.getMessage());
        }
    }
}
