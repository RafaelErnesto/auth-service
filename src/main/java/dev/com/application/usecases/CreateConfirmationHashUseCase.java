package dev.com.application.usecases;

import dev.com.application.exceptions.CreateConfirmationHashException;
import dev.com.domain.entities.ConfirmationHash;
import dev.com.domain.entities.User;
import dev.com.infrastructure.data.repositories.dynamodb.ConfirmationHashRepositoryImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CreateConfirmationHashUseCase {

    @Inject
    ConfirmationHashRepositoryImpl confirmationHashRepository;

    public ConfirmationHash execute(User user){
        try {
            ConfirmationHash confirmationHash = new ConfirmationHash(user.getUserId());
            confirmationHashRepository.insert(confirmationHash);
            return confirmationHash;
        } catch (Exception ex){
            throw new CreateConfirmationHashException("Error while creating hash");
        }
    }
}
