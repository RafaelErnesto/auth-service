package dev.com.application.usecases;

import dev.com.domain.entities.User;

public class ConfirmationHash {
    String value;
    String status;

    String userId;

    ConfirmationHash(User user){
        status = "VALID";
        value = "";
        this.userId = user.getUserId();
    }
}
