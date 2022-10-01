package application.usecases;

import dev.com.application.usecases.AddUserUseCase;
import dev.com.application.usecases.Repository;
import dev.com.domain.entities.User;
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
    Repository userRepository;

    @Test
    void addUserThrowsWhenUserExists(){
        User user = new User("Joseph", "j@mail.com","123456","jh");
        Mockito.when(userRepository.get(user.getEmail())).thenReturn(user);
        Assertions.assertThrows(Exception.class, () -> addUserUseCase.execute(user));
    }

    @Test
    void addUserInsertsWhenUserIsOK(){
        User user = new User("Joseph", "j@mail.com","123456","jh");
        Mockito.when(userRepository.get(user.getEmail())).thenReturn(null);
        addUserUseCase.execute(user);
        Mockito.verify(userRepository).insert(user);
    }
}
