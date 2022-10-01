package dev.com.infrastructure.data.repositories.dynamodb;

import dev.com.application.usecases.Repository;
import dev.com.domain.entities.User;
import dev.com.presentation.dtos.AddUserRequestDto;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements Repository {


    @Override
    public <User> void insert(User input) {

    }

    @Override
    public <String, User> User get(String key) {
        return null;
    }
}
