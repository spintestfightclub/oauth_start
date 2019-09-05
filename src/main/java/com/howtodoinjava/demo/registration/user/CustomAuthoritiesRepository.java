package com.howtodoinjava.demo.registration.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomAuthoritiesRepository extends JpaRepository<CustomAuthorities, String> {
    CustomAuthorities findByUsername(String username);
}
