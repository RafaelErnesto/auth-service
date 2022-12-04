package conf;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfirmationHashDynamoDbTableTestResourceLifeCycManager extends DynamoDbLifeCycleManager {

    @Override
    public Map<String, String> start() {
        return Map.of(
                "auth.confirmation-hash-table-name", getDynamoContainerTableName(),
                "quarkus.dynamodb.endpoint-override", "http://localhost:"+getContainerPort());
    }

    @Override
    public void stop() {

    }

    public String getDynamoContainerTableName() {
        return createTable(dynamoDBContainer.getClient());
    }


    private String createTable(AmazonDynamoDB client) {
        String tableName = "confirmation-hash-table-test";

        List<AttributeDefinition> attributeDefinitions= new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("hash").withAttributeType("S"));
        //attributeDefinitions.add(new AttributeDefinition().withAttributeName("created_at").withAttributeType("S"));

        List<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
        keySchema.add(new KeySchemaElement().withAttributeName("hash").withKeyType(KeyType.HASH));
        //keySchema.add(new KeySchemaElement().withAttributeName("created_at").withKeyType(KeyType.RANGE));

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
