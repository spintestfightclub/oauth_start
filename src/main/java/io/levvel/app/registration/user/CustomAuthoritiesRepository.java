package io.levvel.app.registration.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomAuthoritiesRepository extends JpaRepository<CustomAuthorities, String> {
    CustomAuthorities findByUsername(String username);
}
