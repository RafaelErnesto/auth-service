package dev.com.application.services;

import dev.com.domain.entities.ConfirmationHash;
import dev.com.domain.entities.User;
import dev.com.infrastructure.data.repositories.dynamodb.ConfirmationHashRepositoryImpl;
import dev.com.infrastructure.data.repositories.dynamodb.UserRepositoryImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ConfirmUserCreationService {

    @Inject
    UserRepositoryImpl userRepository;
    @Inject
    ConfirmationHashRepositoryImpl confirmationHashRepository;

    public void execute(String hash){
        ConfirmationHash hashFound = confirmationHashRepository.get(hash);
        userRepository.setUserToActive("");
        confirmationHashRepository.delete(hashFound);
    }
}
