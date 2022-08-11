package dev.com.entities;

public class User {
    String email;
    String name;
    String password;
    String username;

    public User(String name, String email, String password, String username) {
        this.email = email;
        this.name = name;
        this.username = username;
        this.password = this.hashPassword(password);
    }

    private String hashPassword(String pwd) {
        //TO-DO
        //hash de pwd
        return "";
    }

    public Boolean validatePassword(String password) {
        //TO-DO
        //compare the passwords
        return false;
    }
}

