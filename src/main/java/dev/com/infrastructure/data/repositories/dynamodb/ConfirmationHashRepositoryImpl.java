package dev.com.infrastructure.data.repositories.dynamodb;

import dev.com.application.Repository;
import dev.com.domain.entities.ConfirmationHash;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named("confirmationHashRepository")
public class ConfirmationHashRepositoryImpl implements Repository<ConfirmationHash> {
    @Override
    public void insert(ConfirmationHash input) {

    }

    @Override
    public <String> ConfirmationHash get(String key) {
        return null;
    }
}
