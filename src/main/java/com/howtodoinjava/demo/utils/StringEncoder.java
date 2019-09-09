package com.howtodoinjava.demo.utils;

import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Random;

@Service
public class StringEncoder {
    public String encode(String encryptString) {
        Random rand = new Random();
        int randInt = rand.nextInt();
        String bytesEncoded = Base64.getEncoder().withoutPadding().encodeToString(encryptString.getBytes());
        return bytesEncoded + randInt;
    }
}
