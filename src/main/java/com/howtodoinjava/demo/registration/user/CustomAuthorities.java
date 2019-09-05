package com.howtodoinjava.demo.registration.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name="authorities")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomAuthorities{
    @Id
    String username;
    GrantedAuthority authority;
}
