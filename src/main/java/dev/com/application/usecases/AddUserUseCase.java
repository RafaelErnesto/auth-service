package dev.com.application.usecases;

import dev.com.domain.entities.User;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AddUserUseCase {

    Repository userRepository;
    AddUserUseCase(Repository userRepository) {
        this.userRepository = userRepository;
    }

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
