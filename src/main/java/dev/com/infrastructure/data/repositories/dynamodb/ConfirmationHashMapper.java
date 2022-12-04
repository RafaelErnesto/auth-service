package dev.com.infrastructure.data.repositories.dynamodb;

import dev.com.domain.HashStatus;
import dev.com.domain.entities.ConfirmationHash;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

public class ConfirmationHashMapper {

    public static ConfirmationHash toHash(Map<String, AttributeValue> item){
        ConfirmationHash hash = new ConfirmationHash(item.get("hash").s(),item.get("user_id").s());
        hash.setStatus(HashStatus.valueOf(item.get("status").s()));
        return hash;
    }
}
