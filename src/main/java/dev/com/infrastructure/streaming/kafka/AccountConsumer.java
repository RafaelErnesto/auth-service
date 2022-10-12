package dev.com.infrastructure.streaming.kafka;

import dev.com.application.usecases.AddAUserUseCase;
import dev.com.domain.entities.Account;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AccountConsumer {
    @Inject
    private AddAUserUseCase addAUserUseCase;

    @Incoming("accounts-auth")
    public void consume(Account account) {
        addAUserUseCase.execute(account.toUser());
    }
}
