package com.howtodoinjava.demo.registration;

import lombok.Data;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Data
public class CustomUser extends User {
    private final String email;

    public CustomUser(@NonNull String username, @NonNull String password, @NonNull Collection<? extends GrantedAuthority> authorities, @NonNull String email) {
        super(username, password, authorities);

        this.email = email;
    }

    public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, String email) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.email = email;
    }
}
