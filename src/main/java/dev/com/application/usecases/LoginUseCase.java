package dev.com.application.usecases;

import dev.com.application.Repository;
import dev.com.application.usecases.dtos.LoginResponseDto;
import dev.com.domain.entities.User;
import dev.com.infrastructure.data.repositories.dynamodb.UserRepositoryImpl;
import io.smallrye.jwt.build.Jwt;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
public class LoginUseCase {

    @Inject
    //@Named("userRepository")
    UserRepositoryImpl userRepository;

    public LoginResponseDto execute(User user){
        User foundUser = findUser(user);
        return generateLoginResponse(foundUser);
    }

    private User findUser(User user){
        User foundUser = userRepository.get(user);
        if(foundUser == null){
            throw new RuntimeException("Incorrect email or password!");
        }
        return foundUser;
    }
    private LoginResponseDto generateLoginResponse(User user){
        String token =
                Jwt.issuer("auth-service")
                        .upn("auth-service")
                        .groups(new HashSet<>(Arrays.asList("User", "Admin")))
                        .expiresAt(System.currentTimeMillis() + 3600)
                        .sign();
        return new LoginResponseDto(token, token);
    }
}
