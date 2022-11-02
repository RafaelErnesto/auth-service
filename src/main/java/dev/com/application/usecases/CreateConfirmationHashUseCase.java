package dev.com.application.usecases;

import dev.com.domain.entities.User;

public class CreateConfirmationHashUseCase {
    public ConfirmationHash execute(User user){
        ConfirmationHash confirmationHash = new ConfirmationHash(user);
        return confirmationHash;
    }
}
