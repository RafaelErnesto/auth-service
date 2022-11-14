package dev.com.application.usecases.dtos;

import dev.com.domain.entities.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class LoginRequestDto {
    @Email(message = "Email format is not valid")
    String email;
    @Size(min = 8, max = 36, message = "Password must have between 8 and 36 digits")
    String password;

    public LoginRequestDto(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User toUser(){
        return new User("", email, password);
    }
}
