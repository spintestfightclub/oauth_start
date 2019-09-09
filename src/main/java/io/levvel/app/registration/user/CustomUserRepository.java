package io.levvel.app.registration.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NonUniqueResultException;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
    CustomUser findByUsername(String username);
    CustomUser findByEncryptedId(String encryptedId);

    default void saveIfNotExists(CustomUser user) throws NonUniqueResultException {
        CustomUser exists = findByUsername(user.getUsername());
        if(exists != null){
            throw new NonUniqueResultException("Username already exists! Usernames must be unique.");
        }
        this.save(user);
    }
}
