package io.levvel.app.registration.totp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TOTPRepository extends JpaRepository <TOTPDetails, String> {
}
