package application.infraestructure;

import conf.ConfirmationHashDynamoDbTableTestResourceLifeCycManager;
import dev.com.domain.entities.ConfirmationHash;
import dev.com.domain.entities.User;
import dev.com.infrastructure.data.repositories.dynamodb.ConfirmationHashRepositoryImpl;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@QuarkusTest
@QuarkusTestResource(ConfirmationHashDynamoDbTableTestResourceLifeCycManager.class)
public class ConfirmationHashRepositoryTest {
    @Inject
    ConfirmationHashRepositoryImpl confirmationHashRepository;

    @Test
    void insertHash(){
        User user = new User("dummy", "dummy@mail.com","12345667");
        ConfirmationHash hashToBeInserted = new ConfirmationHash(user.getUserId());
        confirmationHashRepository.insert(hashToBeInserted);
        ConfirmationHash insertedHash = confirmationHashRepository.get(hashToBeInserted.getValue());
        Assertions.assertEquals(hashToBeInserted.getValue(), insertedHash.getValue());
    }
}
