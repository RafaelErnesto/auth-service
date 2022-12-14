package application.usecases;

import dev.com.application.usecases.dtos.LoginResponseDto;
import dev.com.application.usecases.LoginUseCase;
import dev.com.domain.entities.User;
import dev.com.presentation.dtos.LoginRequestDto;
import dev.com.infrastructure.data.repositories.dynamodb.UserRepositoryImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
public class LoginUseCaseTest {

    @Inject
    LoginUseCase loginUseCase;

    @InjectMock
    UserRepositoryImpl repository;

    @Test
    public void whenUserWasNotFoundThrows(){
        Mockito.when(repository.get(any())).thenReturn(null);
        Assertions.assertThrows(RuntimeException.class, () -> {loginUseCase.execute(new User("dummy","dummy@mail.com","12345678"));});
    }

    @Test
    public void whenPasswordIsNotCorrectThenThrows(){
        User mockedUser = new User("dummy", "dummy@mail.com", "00000000");
        LoginRequestDto mockedRequest = new LoginRequestDto("dummy@mail.com","12345678");
        Mockito.when(repository.get(any())).thenReturn(mockedUser);
        Assertions.assertThrows(RuntimeException.class, () -> {loginUseCase.execute(mockedRequest.toUser());});
    }

    @Test
    public void whenEmailAndPasswordIsCorrectReturnsToken(){
        User mockedUser = new User("dummy", "dummy@mail.com", "12345678");
        LoginRequestDto mockedRequest = new LoginRequestDto("dummy@mail.com","12345678");
        Mockito.when(repository.get(any())).thenReturn(mockedUser);
        LoginResponseDto response = loginUseCase.execute(mockedRequest.toUser());
        Assertions.assertTrue(response.getToken().length() > 0);
        Assertions.assertTrue(response.getRefreshToken().length() > 0);
    }
}
