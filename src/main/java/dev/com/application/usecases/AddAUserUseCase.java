package dev.com.application.usecases;

import dev.com.domain.entities.Account;
import dev.com.domain.entities.User;
import dev.com.infrastructure.data.repositories.dynamodb.UserRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AddAUserUseCase {

    Repository userRepository;
    AddAUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(User input) {
        if(!this.userAlreadyExists(input.getEmail())){
            this.userRepository.insert(input);
        }
    }

    private boolean userAlreadyExists(String email){
        User userFound = userRepository.get(email);
        return userFound != null;
    }
}
