package dev.com.infrastructure.data.repositories.dynamodb;

import dev.com.application.Repository;
import dev.com.domain.entities.ConfirmationHash;
import dev.com.infrastructure.config.AuthConfigProperties;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class ConfirmationHashRepositoryImpl implements Repository<ConfirmationHash> {


    @Inject
    DynamoDbClient dynamoDB;

    @Inject
    AuthConfigProperties authConfigProperties;

    public String getTableName() {
        return authConfigProperties.confirmationHashTableName();
    }

    @Override
    public void insert(ConfirmationHash input) {
        dynamoDB.putItem(putRequest(input));
    }

    @Override
    public <String> ConfirmationHash get(String key) {
        GetItemRequest getRequest = getRequest((java.lang.String) key);
        GetItemResponse response = dynamoDB.getItem(getRequest);
        if (response.hasItem()) {
            Map<java.lang.String, AttributeValue> item = response.item();
            return ConfirmationHashMapper.toHash(item);
        }
        throw new RuntimeException("Hash not found");
    }

    protected GetItemRequest getRequest(String hash) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("hash", AttributeValue.builder().s(hash).build());

        return GetItemRequest.builder()
                .tableName(getTableName())
                .key(key)
                .build();
    }

    private PutItemRequest putRequest(ConfirmationHash hash) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("hash", AttributeValue.builder().s(hash.getValue()).build());
        item.put("user_id", AttributeValue.builder().s(hash.getUserId()).build());
        item.put("status", AttributeValue.builder().s(hash.getStatus().name()).build());
        item.put("created_at", AttributeValue.builder().s(LocalDateTime.now().toString()).build());

        return PutItemRequest.builder()
                .tableName(getTableName())
                .item(item)
                .build();
    }
}
