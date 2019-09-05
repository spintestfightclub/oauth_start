package com.howtodoinjava.demo.registration.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;


import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="Users")
public class CustomUser {
    @JsonIgnore @Id
    @GeneratedValue Long id;

    String username;
    String password;
    String email;
    GrantedAuthority authority;
    String encryptedId;
    boolean enabled;
}
