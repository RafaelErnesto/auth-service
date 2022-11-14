package dev.com.application.usecases;

import dev.com.application.Repository;
import dev.com.domain.entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
public class CreateUserUseCase {

    @Inject
    @Named("userRepository")
    Repository userRepository;

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
