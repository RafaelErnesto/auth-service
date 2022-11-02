package dev.com.application.usecases;

import dev.com.domain.entities.User;
import dev.com.infrastructure.data.repositories.dynamodb.CreateUserRepositoryImpl;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateUserUseCase {

    CreateUserRepository createUserRepository;
    GetUserRepository getUserRepository;
    CreateUserUseCase(CreateUserRepositoryImpl createUserRepositoryImpl) {
        this.createUserRepository= createUserRepositoryImpl;
    }

    public void execute(User input) {
        if(this.userAlreadyExists(input.getEmail())){
            throw new RuntimeException("User already exists");
        }
        this.createUserRepository.insert(input);
    }

    private boolean userAlreadyExists(String email){
        User userFound = getUserRepository.get(email);
        return userFound != null;
    }
}
