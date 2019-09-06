package com.howtodoinjava.demo.passwordReset;

import com.howtodoinjava.demo.passwordReset.dto.PasswordResetToken;
import com.howtodoinjava.demo.passwordReset.dto.PasswordResetTokenRepository;
import com.howtodoinjava.demo.registration.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
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
