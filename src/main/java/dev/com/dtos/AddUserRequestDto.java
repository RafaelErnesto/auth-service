package dev.com.dtos;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AddUserRequestDto {
    @Email
    String email;
    String name;
    String password;
    String username;
}
