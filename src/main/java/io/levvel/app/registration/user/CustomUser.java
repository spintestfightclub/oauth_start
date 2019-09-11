package io.levvel.app.registration.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="Users")
public class CustomUser {
    @JsonIgnore @Id
    @GeneratedValue Long id;

    @Column(unique = true)
    String username;
    String password;
    String email;
    GrantedAuthority authority;
    String encryptedId;
    boolean enabled;
    boolean accountNonExpired;
    boolean accountNonLocked;
    boolean credentialsNonExpired;
    boolean totpEnabled;
  private String secret;
}
