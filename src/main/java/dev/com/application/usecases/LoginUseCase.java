package dev.com.application.usecases;

import dev.com.domain.entities.User;
import dev.com.infrastructure.data.repositories.dynamodb.UserRepository;
import io.smallrye.jwt.build.Jwt;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
public class LoginUseCase {

    @Inject
    Repository repository;

    LoginUseCase(UserRepository repository){
        this.repository = repository;
    }

    public LoginResponseDto execute(LoginRequestDto request){
        User foundUser = findUser(request);
        return generateLoginResponse(foundUser);
    }

    private User findUser(LoginRequestDto request){
        User foundUser = repository.get(request);
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
