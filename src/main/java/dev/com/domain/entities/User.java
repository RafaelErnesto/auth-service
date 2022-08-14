package dev.com.domain.entities;


import java.math.BigInteger;
import java.security.MessageDigest;

public class User {
    String email;
    String name;
    String password;
    String username;

    public User(String name, String email, String password, String username)  {
        this.email = email;
        this.name = name;
        this.username = username;
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

    public Boolean validatePassword(String password) {
        return hashPassword(password).equals(this.password);
    }
}

