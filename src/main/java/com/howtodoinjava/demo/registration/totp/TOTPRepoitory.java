package com.howtodoinjava.demo.registration.totp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TOTPRepoitory extends JpaRepository <TOTPDetails, String> {
}
