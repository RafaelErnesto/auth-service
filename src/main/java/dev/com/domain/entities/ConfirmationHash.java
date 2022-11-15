package dev.com.domain.entities;

import dev.com.domain.HashStatus;

import java.util.UUID;

public class ConfirmationHash {
    String value;
    HashStatus status;

    String userId;

    public ConfirmationHash(User user){
        status = HashStatus.VALID;
        value = generateHash();
        userId = user.getUserId();
    }

    public String getValue() {
        return value;
    }


    public HashStatus getStatus() {
        return status;
    }

    public void setInvalid() {
        status = HashStatus.INVALID;
    }

    private String generateHash(){
        return  UUID.randomUUID().toString();
    }
}
