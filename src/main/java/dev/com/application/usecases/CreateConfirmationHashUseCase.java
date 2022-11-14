package dev.com.application.usecases;

import dev.com.application.Repository;
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
        ConfirmationHash confirmationHash = new ConfirmationHash(user);
        confirmationHashRepository.insert(confirmationHash);
        return confirmationHash;
    }
}
