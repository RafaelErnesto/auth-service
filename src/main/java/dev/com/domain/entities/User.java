package dev.com.domain.entities;

import dev.com.domain.Password;
import dev.com.domain.UserId;
import dev.com.domain.UserStatus;

public class User {

    String email;
    String name;
    Password password;
    UserId userId;
    UserStatus status;
    public User(String name, String email){
        this.name = name;
        this.email = email;
    };
    public User(String name, String email, String password)  {
        this.email = email;
        this.name = name;
        this.status = UserStatus.PENDING;
        this.password = new Password(password);
        this.userId = new UserId();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password.getPasswordHash();
    }

    public String getUserId(){
        return userId.getUserId();
    }

    public void setUserId(String userId){
        if(this.userId != null){
            throw new RuntimeException("Cannot reset user id");
        }

        this.userId = new UserId(userId);
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Boolean validatePassword(String password) {
        Password pwdToCompare = new Password(password);
        return pwdToCompare.equals(this.password);
    }
}
