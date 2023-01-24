package conf;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsersDynamoDbTableTestResourceLifeCycleManager extends DynamoDbLifeCycleManager {

    @Override
    public Map<String, String> start() {
        return Map.of(
                "auth.user-table-name", getDynamoContainerTableName(),
                "quarkus.dynamodb.endpoint-override", "http://localhost:"+getContainerPort());
    }

    @Override
    public void stop() {

    }

    public String getDynamoContainerTableName() {
        return createTable(dynamoDBContainer.getClient());
    }


    private String createTable(AmazonDynamoDB client) {
        String tableName = "user-table-test";

        List<AttributeDefinition> attributeDefinitions= new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("email").withAttributeType("S"));
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("status").withAttributeType("S"));

        List<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
        keySchema.add(new KeySchemaElement().withAttributeName("email").withKeyType(KeyType.HASH));
        keySchema.add(new KeySchemaElement().withAttributeName("status").withKeyType(KeyType.RANGE));

        CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(keySchema)
                .withAttributeDefinitions(attributeDefinitions)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(5L)
                        .withWriteCapacityUnits(6L));

        client.createTable(request);
        return tableName;
    }
}
