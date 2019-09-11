package io.levvel.app.registration.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NonUniqueResultException;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
    CustomUser findByUsername(String username);
    CustomUser findByEncryptedId(String encryptedId);
}
