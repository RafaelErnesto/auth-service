package dev.com.infrastructure.data.repositories.dynamodb;

import dev.com.domain.UserStatus;
import dev.com.domain.entities.User;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

public class UserMapper {

    public static User toUser(Map<String, AttributeValue> item){
        User user = new User(item.get("name").s(), item.get("email").s());
        user.setStatus(UserStatus.valueOf(item.get("user_status").s()));
        user.setUserId(item.get("user_id").s());
        return user;
    }
}
