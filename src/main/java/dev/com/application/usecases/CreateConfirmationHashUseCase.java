package dev.com.application.usecases;

import dev.com.application.Repository;
import dev.com.application.exceptions.CreateConfirmationHashException;
import dev.com.domain.entities.ConfirmationHash;
import dev.com.domain.entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
public class CreateConfirmationHashUseCase {

    @Inject
    @Named("confirmationHashRepository")
    Repository confirmationHashRepository;

    public ConfirmationHash execute(User user){
        try {
            ConfirmationHash confirmationHash = new ConfirmationHash(user);
            confirmationHashRepository.insert(confirmationHash);
            return confirmationHash;
        } catch (Exception ex){

            throw new CreateConfirmationHashException("Error while creating hash");
        }
    }
}
