package dev.com.application.services;

import dev.com.application.usecases.CreateConfirmationHashUseCase;
import dev.com.application.usecases.CreateUserUseCase;
import dev.com.application.usecases.UserCreationEventEmitter;
import dev.com.domain.entities.ConfirmationHash;
import dev.com.domain.entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CreateUserService {
    @Inject
    CreateUserUseCase createUserUseCase;

    @Inject
    CreateConfirmationHashUseCase createConfirmationHashUseCase;

    @Inject
    UserCreationEventEmitter userCreationEventEmitter;

    public void execute(User input){
        try{
            createUserUseCase.execute(input);
            ConfirmationHash confirmationHash = createConfirmationHashUseCase.execute(input);
            userCreationEventEmitter.execute(confirmationHash);
        }catch(Exception ex){

        }
    }
}
