package dev.com.application.services;

import dev.com.application.usecases.CreateConfirmationHashUseCase;
import dev.com.application.usecases.CreateUserUseCase;
import dev.com.domain.entities.User;

import javax.inject.Inject;

public class CreateUserService {
    @Inject
    CreateUserUseCase createUserUseCase;

    @Inject
    CreateConfirmationHashUseCase createConfirmationHashUseCase;

    public void execute(User input){
        try{
            createUserUseCase.execute(input);
            createConfirmationHashUseCase.execute(input);
        }catch(Exception ex){

        }
    }
}
