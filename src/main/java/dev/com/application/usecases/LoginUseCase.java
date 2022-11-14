package dev.com.application.usecases;

import dev.com.application.Repository;
import dev.com.application.usecases.dtos.LoginRequestDto;
import dev.com.application.usecases.dtos.LoginResponseDto;
import dev.com.domain.entities.User;
import io.smallrye.jwt.build.Jwt;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
public class LoginUseCase {

    @Inject
    @Named("userRepository")
    Repository userRepository;

    public LoginResponseDto execute(LoginRequestDto request){
        User foundUser = findUser(request);
        return generateLoginResponse(foundUser);
    }

    private User findUser(LoginRequestDto request){
        User foundUser = userRepository.get(request);
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
