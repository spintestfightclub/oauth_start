package com.howtodoinjava.demo.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.Random;

@Service
public class StringEncoder {
    public String encode(String encryptString) throws NoSuchAlgorithmException {
        Random rand = new Random();
        int randInt = rand.nextInt();
        String bytesEncoded = Base64.getEncoder().withoutPadding().encodeToString(encryptString.getBytes());
        return bytesEncoded + randInt;
    }
}
