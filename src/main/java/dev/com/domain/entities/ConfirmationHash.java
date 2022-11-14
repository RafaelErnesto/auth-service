package dev.com.domain.entities;

import java.util.UUID;

public class ConfirmationHash {
    String value;
    String status;

    String userId;

    public ConfirmationHash(User user){
        status = "VALID";
        value = generateHash();
        this.userId = user.getUserId();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String generateHash(){
        return  UUID.randomUUID().toString();
    }
}
