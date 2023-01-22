package dev.com.infrastructure.data.repositories.dynamodb;


import dev.com.application.Repository;
import dev.com.domain.UserStatus;
import dev.com.domain.entities.User;
import dev.com.infrastructure.config.AuthConfigProperties;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        GetItemRequest getRequest = createGetRequest(email, UserStatus.ACTIVE);
        GetItemResponse response = dynamoDB.getItem(getRequest);
        if (response.hasItem()) {
            Map<java.lang.String, AttributeValue> item = response.item();
            return UserMapper.toUser(item);
        }
       return null;
    }

    public User getPendingUserByEmail(String email) {
        GetItemRequest getRequest = createGetRequest(email, UserStatus.PENDING);
        GetItemResponse response = dynamoDB.getItem(getRequest);
        if (response.hasItem()) {
            Map<java.lang.String, AttributeValue> item = response.item();
            return UserMapper.toUser(item);
        }
        return null;
    }

    public List<User> getPendingOrActiveUserByEmail(String email) {
        QueryRequest queryRequest = createQueryRequest(email, List.of(UserStatus.ACTIVE, UserStatus.PENDING));
        QueryResponse response = dynamoDB.query(queryRequest);
        if (response.hasItems()) {
            return response.items()
                    .stream()
                    .map(item -> UserMapper.toUser(item))
                    .collect(Collectors.toList());
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


    protected GetItemRequest createGetRequest(String email, UserStatus status) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("email", AttributeValue.builder().s(email).build());
        key.put("status", AttributeValue.builder().s(status.name()).build());
        return GetItemRequest.builder()
                .tableName(getTableName())
                .key(key)
                .build();
    }

    protected QueryRequest createQueryRequest(String email, List<UserStatus> statusList) {
        Map<String, AttributeValue> keyExpressionValue = new HashMap<>();
        keyExpressionValue.put("email", AttributeValue.builder().s(email).build());

        Collection<AttributeValue> statusValues = statusList.stream()
                .map(item -> AttributeValue.builder().s(item.toString()).build())
                .collect(Collectors.toList());
        keyExpressionValue.put(":status_list", AttributeValue.builder().l(statusValues).build());

        return QueryRequest.builder()
                .tableName(getTableName())
                .keyConditionExpression("email = :email and status in :status_list")
                .expressionAttributeValues(keyExpressionValue)
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
