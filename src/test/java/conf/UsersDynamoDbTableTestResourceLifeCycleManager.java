package conf;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.testcontainers.dynamodb.DynaliteContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsersDynamoDbTableTestResourceLifeCycleManager implements QuarkusTestResourceLifecycleManager {
    private DockerImageName dynaliteImage = DockerImageName.parse(
            "quay.io/testcontainers/dynalite:v1.2.1-1"
    );

    DynaliteContainer dynamoDBContainer = startContainer();

    @Override
    public Map<String, String> start() {
        return Map.of(
                "auth.user-table-name", getDynamoContainerTableName(),
                "quarkus.dynamodb.endpoint-override", "http://localhost:"+getContainerPort());
    }

    @Override
    public void stop() {

    }

    private DynaliteContainer startContainer() {
        DynaliteContainer dynaliteContainer = new DynaliteContainer(dynaliteImage);
        dynaliteContainer.start();
        return dynaliteContainer;
    }

    public String getDynamoContainerTableName() {
        return createTable(dynamoDBContainer.getClient());
    }

    public String getContainerPort() {
        return dynamoDBContainer.getFirstMappedPort().toString();
    }

    private String createTable(AmazonDynamoDB client) {
        String tableName = "user-table-test";

        List<AttributeDefinition> attributeDefinitions= new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("email").withAttributeType("S"));

        List<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
        keySchema.add(new KeySchemaElement().withAttributeName("email").withKeyType(KeyType.HASH));

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
