package application.usecases;

import dev.com.application.usecases.AddUserUseCase;
import dev.com.domain.entities.User;
import dev.com.infrastructure.data.repositories.dynamodb.UserRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

@QuarkusTest
public class AddUserUseCaseTest {

    @Inject
    AddUserUseCase addUserUseCase;

    @InjectMock
    UserRepository userRepository;

    @Test
    void addUserThrowsWhenUserExists(){
        Mockito.when(userRepository.get("")).thenReturn(null);
        Assertions.assertThrows(Exception.class, () -> addUserUseCase.execute(new User("Joseph", "j@mail.com","123456","jh")));
    }
}
