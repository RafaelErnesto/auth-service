package dev.com.domain;

import dev.com.domain.entities.User;

public class UserBuilder {
    private String email;
    private String name;
    private String password;
    private UserId userId;
    private UserStatus status;

    public UserBuilder(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public UserBuilder password(String password){
        this.password = password;
        return this;
    }

    public UserBuilder status(UserStatus status) {
        this.status = status;
        return this;
    }

    public UserBuilder userId(UserId userId) {
        this.userId = userId;
        return this;
    }

    public User build(){
        if(password == null) {
            throw new RuntimeException("Cannot build user without a password");
        }
        User user = new User(this.name, this.email, this.password);
        if(status != null) {
            user.setStatus(status);
        }
        return user;
    }
}
