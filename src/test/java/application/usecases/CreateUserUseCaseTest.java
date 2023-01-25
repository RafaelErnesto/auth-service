package application.usecases;

import dev.com.application.usecases.CreateUserUseCase;
import dev.com.common.exception.customexceptions.UserExistsException;
import dev.com.domain.entities.User;
import dev.com.infrastructure.data.repositories.dynamodb.UserRepositoryImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.util.List;

@QuarkusTest
public class CreateUserUseCaseTest {

    @Inject
    CreateUserUseCase createUserUseCase;

    @InjectMock
    UserRepositoryImpl userRepository;

    @Test
    void addUserThrowsWhenUserExists(){
        User user = new User("Joseph", "j@mail.com","123456");
        Mockito.when(userRepository.getPendingOrActiveUserByEmail(user.getEmail())).thenReturn(user);
        Assertions.assertThrows(UserExistsException.class, () -> createUserUseCase.execute(user));
    }

    @Test
    void addUserInsertsWhenUserIsOK(){
        User user = new User("Joseph", "j@mail.com","12345678");
        Mockito.when(userRepository.get(user.getEmail())).thenReturn(null);
        createUserUseCase.execute(user);
        Mockito.verify(userRepository).insert(user);
    }
}
