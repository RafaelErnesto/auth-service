package dev.com.infrastructure.streaming.kafka;

import dev.com.application.usecases.AddUserUseCase;
import dev.com.domain.entities.Account;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AccountConsumer {
    @Inject
    private AddUserUseCase addUserUseCase;

    @Incoming("accounts-auth")
    public void consume(Account account) {
        addUserUseCase.execute(account.toUser());
    }
}
