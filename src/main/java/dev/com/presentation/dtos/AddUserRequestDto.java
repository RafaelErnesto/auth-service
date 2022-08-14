package dev.com.presentation.dtos;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@ApplicationScoped
public class AddUserRequestDto {
    @Email(message = "Email format is not valid")
    String email;
    @Size(min = 3, max = 150, message = "Name must have between 3 and 150 characters")
    String name;
    @Size(min = 8, max = 36, message = "Password must have between 8 and 36 digits")
    String password;
    @Size(min = 3, max = 30, message = "Username must have between 3 and 30 characters")
    String username;
}
