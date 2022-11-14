package dev.com.domain.entities;

public class ConfirmationHash {
    String value;
    String status;

    String userId;

    public ConfirmationHash(User user){
        status = "VALID";
        value = "";
        this.userId = user.getUserId();
    }
}
