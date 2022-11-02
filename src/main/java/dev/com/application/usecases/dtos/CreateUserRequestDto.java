package dev.com.application.usecases.dtos;

import dev.com.domain.entities.User;

public class CreateUserRequestDto {
    String name;
    String email;
    String password;

    public User toUser(){
        return new User(name, email, password);
    }
}
