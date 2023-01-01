package dev.com.presentation.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.com.domain.entities.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestDto {
    @JsonProperty(value = "name")
    @NotNull(message = "Name cannot be null")
    String name;

    @JsonProperty(value = "email")
    @Email(message="Must be a valid email format")
    String email;

    @JsonProperty(value = "password")
    @Size(min = 10, message = "Password must have at least 8 digits")
    String password;

    public User toUser(){
        return new User(name, email, password);
    }
}
