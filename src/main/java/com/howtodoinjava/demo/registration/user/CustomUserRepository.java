package com.howtodoinjava.demo.registration.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
    CustomUser findByUsername(String username);
    CustomUser findByEncryptedId(String encryptedId);

    default CustomUser saveIfNotExists(CustomUser user){
        CustomUser exists = findByUsername(user.getUsername());
        if(exists != null){
            return exists;
        }
        this.save(user);
        return user;
    }
}
