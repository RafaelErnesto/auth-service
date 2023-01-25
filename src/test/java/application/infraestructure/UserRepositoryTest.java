package application.infraestructure;

import conf.UsersDynamoDbTableTestResourceLifeCycleManager;
import dev.com.domain.UserStatus;
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
    void whenInsertingAUserItsStatusMustBePending(){
        User user = new User("Dummy","dummy@email.com","12345678");
        userRepository.insert(user);

        User insertedUser = userRepository.getPendingUserByEmail("dummy@email.com");
        Assertions.assertEquals(user.getUserId(), insertedUser.getUserId());
        Assertions.assertEquals(user.getEmail(), insertedUser.getEmail());
        Assertions.assertEquals(user.getName(), insertedUser.getName());
        Assertions.assertEquals(UserStatus.PENDING, insertedUser.getStatus());
    }

    @Test
    void whenSetUserAsActiveTheStatusMustBeActive(){
        User user = new User("Dummy","dummy@email.com","12345678");
        userRepository.insert(user);

        userRepository.setUserToActive("dummy@email.com");
        User activeUser = userRepository.get("dummy@email.com");
        Assertions.assertEquals(user.getUserId(), activeUser.getUserId());
        Assertions.assertEquals(user.getEmail(), activeUser.getEmail());
        Assertions.assertEquals(user.getName(), activeUser.getName());
        Assertions.assertEquals(UserStatus.ACTIVE, activeUser.getStatus());
    }
}
