package conf;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.dynamodb.DynaliteContainer;
import org.testcontainers.utility.DockerImageName;


abstract public class DynamoDbLifeCycleManager implements QuarkusTestResourceLifecycleManager {
    private DockerImageName dynaliteImage = DockerImageName.parse(
            "quay.io/testcontainers/dynalite:v1.2.1-1"
    );

    DynaliteContainer dynamoDBContainer = startContainer();

    private DynaliteContainer startContainer() {
        DynaliteContainer dynaliteContainer = new DynaliteContainer(dynaliteImage);
        dynaliteContainer.start();
        return dynaliteContainer;
    }

    abstract public String getDynamoContainerTableName();

    public String getContainerPort() {
        return dynamoDBContainer.getFirstMappedPort().toString();
    }

}
