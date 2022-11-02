package dev.com.domain;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Password {
    private String password;

    public Password(String password) {
        this.password = hashPassword(password);
    }

    private String hashPassword(String pwd)  {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(pwd.getBytes("UTF-8"));
            BigInteger number = new BigInteger(1, hash);
            StringBuilder hexString = new StringBuilder(number.toString(16));

            while (hexString.length() < 64)
            {
                hexString.insert(0, '0');
            }

            return hexString.toString();
        }catch(Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    public String getPasswordHash() {
        return password;
    }
}
