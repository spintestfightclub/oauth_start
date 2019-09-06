package com.howtodoinjava.demo.registration.totp;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@RequiredArgsConstructor
public class TOTPDetails {
    @Id
    @GeneratedValue
    private final String username;
    private final String secret;

}
