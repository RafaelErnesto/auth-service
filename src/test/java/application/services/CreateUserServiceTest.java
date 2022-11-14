package application.services;

import dev.com.application.exceptions.CreateConfirmationHashException;
import dev.com.application.exceptions.CreateUserException;
import dev.com.application.services.CreateUserService;
import dev.com.application.usecases.CreateConfirmationHashUseCase;
import dev.com.application.usecases.CreateUserUseCase;
import dev.com.application.usecases.UserCreationEventEmitter;
import dev.com.domain.entities.User;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
public class CreateUserServiceTest {

    @InjectMock
    CreateUserUseCase createUserUseCase;

    @InjectMock
    CreateConfirmationHashUseCase createConfirmationHashUseCase;

    @InjectMock
    UserCreationEventEmitter userCreationEventEmitter;


    @Test
    void shouldThrowCreateUserExceptionWhenAnErrorOccur(){
        CreateUserService sut = new CreateUserService();
        User mockedUser = new User("dummy","dummy@mail.com","123456");
        Mockito.when(createConfirmationHashUseCase.execute(mockedUser)).thenThrow(CreateConfirmationHashException.class);
        Assertions.assertThrows(CreateUserException.class, () -> { sut.execute(mockedUser); });
    }
}
