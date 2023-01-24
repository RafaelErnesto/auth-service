package application.infraestructure;

import conf.UsersDynamoDbTableTestResourceLifeCycleManager;
import dev.com.domain.entities.User;
import dev.com.infrastructure.data.repositories.dynamodb.UserRepositoryImpl;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import javax.inject.Inject;


@QuarkusTest
@QuarkusTestResource(value = UsersDynamoDbTableTestResourceLifeCycleManager.class, restrictToAnnotatedClass = true)
public class UserRepositoryTest {

    @Inject
    UserRepositoryImpl userRepository;

    @Test
    void insertUser(){
        User user = new User("Dummy","dummy@email.com","12345678");
        userRepository.insert(user);

        User insertedUser = userRepository.getPendingUserByEmail("dummy@email.com");
        Assertions.assertEquals(user.getUserId(), insertedUser.getUserId());
        Assertions.assertEquals(user.getEmail(), insertedUser.getEmail());
        Assertions.assertEquals(user.getName(), insertedUser.getName());
        Assertions.assertEquals(user.getStatus(), insertedUser.getStatus());
    }
}
