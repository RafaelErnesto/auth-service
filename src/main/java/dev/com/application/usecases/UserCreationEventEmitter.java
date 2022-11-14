package dev.com.application.usecases;

import dev.com.application.Emitter;
import dev.com.domain.entities.ConfirmationHash;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
public class UserCreationEventEmitter {
    @Inject
    @Named("usrCreationEmitter")
    Emitter userCreationEmitter;


    public UserCreationEventEmitter(Emitter userCreationEmitter) {
        this.userCreationEmitter = userCreationEmitter;
    }

    public void execute(ConfirmationHash hash){
        userCreationEmitter.execute(hash);
    }
}
