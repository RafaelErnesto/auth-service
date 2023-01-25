package dev.com.infrastructure.data.repositories.dynamodb;


import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.xspec.S;
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
        QueryRequest queryRequest = createQueryRequestByStatusIndex(email);
        QueryResponse response = dynamoDB.query(queryRequest);
        if (response.hasItems()) {
            return response.items()
                    .stream().map(item -> UserMapper.toUser(item))
                    .filter(user -> user.getStatus().equals(UserStatus.ACTIVE))
                    .collect(Collectors.toList()).get(0);
        }
        return null;
    }

    public User getPendingUserByEmail(String email) {
        QueryRequest queryRequest = createQueryRequestByStatusIndex(email);
        QueryResponse response = dynamoDB.query(queryRequest);
        if (response.hasItems()) {
            return response.items()
                    .stream()
                    .map(item -> UserMapper.toUser(item))
                    .filter(user -> user.getStatus().equals(UserStatus.PENDING))
                    .collect(Collectors.toList()).get(0);
        }
        return null;
    }

    public User getPendingOrActiveUserByEmail(String email) {
        QueryRequest queryRequest = createQueryRequestByStatusIndex(email);
        QueryResponse response = dynamoDB.query(queryRequest);
        List<UserStatus> statusList = List.of(UserStatus.ACTIVE, UserStatus.PENDING);
        if (response.hasItems()) {
            return response.items()
                    .stream()
                    .map(item -> UserMapper.toUser(item))
                    .filter(user -> statusList.contains(user.getStatus()))
                    .collect(Collectors.toList()).get(0);
        }
        return null;
    }

    @Override
    public void update(User input) {

    }

    @Override
    public void delete(User input) {

    }

    public void setUserToActive(String email) {
        User foundUser = getPendingUserByEmail(email);
        if(foundUser == null) {
            throw new RuntimeException("User not found");

        }
        UpdateItemRequest updateItemRequest = createUpdateRequest(email,
                foundUser.getUserId(),
                "SET user_status = :status",
                Map.of(":status", AttributeValue.builder().s(UserStatus.ACTIVE.toString()).build()));
        dynamoDB.updateItem(updateItemRequest);
    }


    protected GetItemRequest createGetRequest(String email, String userId) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("email", AttributeValue.builder().s(email).build());
        key.put("user_id", AttributeValue.builder().s(userId).build());
        return GetItemRequest.builder()
                .tableName(getTableName())
                .key(key)
                .build();
    }

    protected QueryRequest createQueryRequestByStatusIndex(String email) {

        return QueryRequest.builder()
                .tableName(getTableName())
                .indexName("email-status-index")
                .keyConditionExpression("email = :email")
                .expressionAttributeValues(Map.of(
                        ":email", AttributeValue.builder().s(email).build()))
                .scanIndexForward(true)
                .build();
    }

    private PutItemRequest putRequest(User user, UserStatus status) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("email", AttributeValue.builder().s(user.getEmail()).build());
        item.put("name", AttributeValue.builder().s(user.getName()).build());
        item.put("password", AttributeValue.builder().s(user.getPassword()).build());
        item.put("user_status", AttributeValue.builder().s(status.name()).build());
        item.put("user_id", AttributeValue.builder().s(user.getUserId()).build());
        item.put("created_at", AttributeValue.builder().s(LocalDateTime.now().toString()).build());

        return PutItemRequest.builder()
                .tableName(getTableName())
                .item(item)
                .build();
    }

    private UpdateItemRequest createUpdateRequest(String email,String userId, String expression, Map<String, AttributeValue> updateAttributes) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("email", AttributeValue.builder().s(email).build());
        key.put("user_id", AttributeValue.builder().s(userId).build());

        return UpdateItemRequest.builder()
                .tableName(getTableName())
                .key(key)
                .updateExpression(expression)
                .expressionAttributeValues(updateAttributes)
                .build();
    }
}
