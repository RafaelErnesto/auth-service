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
        User user = new User("Dummy","dummy1@email.com","12345678");
        userRepository.insert(user);

        User insertedUser = userRepository.getPendingUserByEmail("dummy1@email.com");
        Assertions.assertEquals(user.getUserId(), insertedUser.getUserId());
        Assertions.assertEquals(user.getEmail(), insertedUser.getEmail());
        Assertions.assertEquals(user.getName(), insertedUser.getName());
        Assertions.assertEquals(UserStatus.PENDING, insertedUser.getStatus());
    }

    @Test
    void whenSetUserAsActiveTheStatusMustBeActive(){
        User user = new User("Dummy","dummy2@email.com","12345678");
        userRepository.insert(user);

        userRepository.setUserToActive("dummy2@email.com");
        User activeUser = userRepository.get("dummy2@email.com");
        Assertions.assertEquals(user.getUserId(), activeUser.getUserId());
        Assertions.assertEquals(user.getEmail(), activeUser.getEmail());
        Assertions.assertEquals(user.getName(), activeUser.getName());
        Assertions.assertEquals(UserStatus.ACTIVE, activeUser.getStatus());
    }

    @Test
    void whenSetANonExistentUserAsActiveMustThrow(){
        Assertions.assertThrows(RuntimeException.class, () -> userRepository.setUserToActive("dummy3@email.com"));
    }

    @Test
    void whenTryToInsertAnExistentUserThatIsPendingMustThrow(){
        User user = new User("Dummy","dummy4@email.com","12345678");
        userRepository.insert(user);
        Assertions.assertThrows(RuntimeException.class, () -> userRepository.insert(user));
    }
}
