package dev.com.application.usecases;

import dev.com.domain.entities.User;
import dev.com.infrastructure.data.repositories.dynamodb.UserRepositoryImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CreateUserUseCase {

    @Inject
    UserRepositoryImpl userRepository;

    public void execute(User input) {
        if(this.userAlreadyExists(input.getEmail())){
            throw new RuntimeException("User already exists");
        }
        this.userRepository.insert(input);
    }

    private boolean userAlreadyExists(String email){
        User userFound = userRepository.get(email);
        return userFound != null;
    }
}
