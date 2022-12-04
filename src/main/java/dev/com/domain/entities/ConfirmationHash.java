package dev.com.domain.entities;

import dev.com.domain.HashStatus;

import java.util.UUID;

public class ConfirmationHash {
    String value;
    HashStatus status;
    String userId;

    public ConfirmationHash(String value, String userId){
        this.value = value;
        this.userId = userId;
    }

    public ConfirmationHash(String userId){
        status = HashStatus.VALID;
        value = generateHash();
        this.userId = userId;
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
    public String getUserId(){
        return userId;
    }

    public void setStatus(HashStatus status){
        this.status = status;
    }
    private String generateHash(){
        return  UUID.randomUUID().toString();
    }

}
