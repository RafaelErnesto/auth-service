package dev.com.domain;

import dev.com.domain.entities.User;

import java.util.UUID;

public class UserId {
    private String userId;

    public UserId(){
        this.userId = generateUserId();

    }
    public UserId(String userId ){
        this.userId = userId != null && userId.length() > 0 ? userId :generateUserId();
    }

    public String getUserId() {
        return userId;
    }

    private String generateUserId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
