package io.levvel.app.config;

import io.levvel.app.registration.user.CustomUser;
import io.levvel.app.registration.user.CustomUserRepository;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.LinkedHashMap;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {
    @Autowired CustomUserRepository customUserRepository;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) auth.getDetails();
        String verificationCode = (String) properties.get("code");
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        if(customUser == null){
            throw new BadCredentialsException("Invalid username or password");
        }
        if(customUser.isTotpEnabled()){
            Totp totp = new Totp(customUser.getSecret());
            if(!isValidLong(verificationCode) || !totp.verify(verificationCode)){
                throw new BadCredentialsException("Invalid verification code");
            }
        }

        Authentication result = super.authenticate(auth);
        return new UsernamePasswordAuthenticationToken(customUser, result.getCredentials(), result.getAuthorities());
    }

    private boolean isValidLong(String code){
        try{
            Long.parseLong(code);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    @Override
    public boolean supports(Class<?> authentication){
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
