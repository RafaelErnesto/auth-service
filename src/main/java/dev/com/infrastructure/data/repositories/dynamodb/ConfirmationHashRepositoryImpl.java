package dev.com.infrastructure.data.repositories.dynamodb;

import dev.com.application.Repository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named("confirmationHashRepository")
public class ConfirmationHashRepositoryImpl implements Repository {
    @Override
    public <ConfirmationHash> void insert(ConfirmationHash input) {

    }

    @Override
    public <String, ConfirmationHash> ConfirmationHash get(String key) {
        return null;
    }
}
