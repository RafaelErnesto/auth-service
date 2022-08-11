package dev.com.usecases;

import dev.com.dtos.AddUserRequestDto;
import dev.com.repositories.UserRepository;

import javax.inject.Inject;

public class AddUserUseCase {

    @Inject
    UserRepository userRepository;

    public String execute(AddUserRequestDto request) {
        userRepository.insertUser(request);
        return "";
    }
}
