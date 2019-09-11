package io.levvel.app.passwordReset;

import io.levvel.app.passwordReset.dto.PasswordResetToken;
import io.levvel.app.passwordReset.dto.PasswordResetTokenRepository;
import io.levvel.app.registration.user.CustomUser;
import io.levvel.app.registration.user.CustomUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PasswordResetService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final CustomUserRepository customUserRepository;

    public PasswordResetToken createPasswordResetToken(String username) {
        final PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUsername(username);
        passwordResetTokenRepository.save(token);
        return token;
    }

    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);
        if (passToken == null) {
            return "invalidToken";
        }
        CustomUser customUser = customUserRepository.findByUsername(passToken.getUsername());
        final Authentication auth = new UsernamePasswordAuthenticationToken(customUser, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }
}
