package dev.com.infrastructure.data.repositories.dynamodb;

import dev.com.application.Repository;
import dev.com.domain.UserStatus;
import dev.com.domain.entities.User;
import dev.com.infrastructure.config.AuthConfigProperties;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class UserRepositoryImpl implements Repository<User> {

    @Inject
    DynamoDbClient dynamoDB;

    @Inject
    AuthConfigProperties authConfigProperties;

    public String getTableName() {
        return authConfigProperties.userTableName();
    }

    @Override
    public void insert(User input) {
        dynamoDB.putItem(putRequest(input, UserStatus.PENDING));
    }

    @Override
    public User get(String email) {
        GetItemRequest getRequest = createGetRequest(email, List.of(UserStatus.ACTIVE));
        GetItemResponse response = dynamoDB.getItem(getRequest);
        if (response.hasItem()) {
            Map<java.lang.String, AttributeValue> item = response.item();
            return UserMapper.toUser(item);
        }
       return null;
    }

    public User getPendingUserByEmail(String email) {
        GetItemRequest getRequest = createGetRequest(email, List.of(UserStatus.PENDING));
        GetItemResponse response = dynamoDB.getItem(getRequest);
        if (response.hasItem()) {
            Map<java.lang.String, AttributeValue> item = response.item();
            return UserMapper.toUser(item);
        }
        return null;
    }

    public User getPendingOrActiveUserByEmail(String email) {
        GetItemRequest getRequest = createGetRequest(email, List.of(UserStatus.ACTIVE, UserStatus.PENDING));
        GetItemResponse response = dynamoDB.getItem(getRequest);
        if (response.hasItem()) {
            Map<java.lang.String, AttributeValue> item = response.item();
            return UserMapper.toUser(item);
        }
        return null;
    }

    @Override
    public void update(User input) {

    }

    @Override
    public void delete(User input) {

    }

    public void updateStatus(UserStatus status) {

    }


    protected GetItemRequest createGetRequest(String email, List<UserStatus> statusList) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("email", AttributeValue.builder().s(email).build());
        //key.put("status", AttributeValue.builder().s(status.name()).build());
        return GetItemRequest.builder()
                .tableName(getTableName())
                .key(key)
                .build();
    }

    private PutItemRequest putRequest(User user, UserStatus status) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("email", AttributeValue.builder().s(user.getEmail()).build());
        item.put("name", AttributeValue.builder().s(user.getName()).build());
        item.put("password", AttributeValue.builder().s(user.getPassword()).build());
        item.put("status", AttributeValue.builder().s(status.name()).build());
        item.put("user_id", AttributeValue.builder().s(user.getUserId()).build());
        item.put("created_at", AttributeValue.builder().s(LocalDateTime.now().toString()).build());

        return PutItemRequest.builder()
                .tableName(getTableName())
                .item(item)
                .build();
    }
}
