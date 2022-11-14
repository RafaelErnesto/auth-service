package dev.com.infrastructure.event;

import dev.com.application.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named("usrCreationEmitter")
public class UserCreationEventEmitterImpl implements Emitter {
    @Override
    public <ConfirmationHash> void execute(ConfirmationHash payload) {

    }
}
