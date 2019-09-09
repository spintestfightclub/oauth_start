package io.levvel.app.passwordReset;

import io.levvel.app.passwordReset.dto.PasswordResetToken;
import io.levvel.app.passwordReset.dto.PasswordResetTokenRepository;
import io.levvel.app.registration.user.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
public class PasswordResetService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final JdbcUserDetailsManager jdbcUserDetailsManager;

    public PasswordResetService(PasswordResetTokenRepository passwordResetTokenRepository, JdbcUserDetailsManager jdbcUserDetailsManager) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.jdbcUserDetailsManager = jdbcUserDetailsManager;
    }

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
        CustomUserDetails user = (CustomUserDetails) jdbcUserDetailsManager.loadUserByUsername(passToken.getUsername());
        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }
}
