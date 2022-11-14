package application.usecases;

import dev.com.application.usecases.dtos.LoginResponseDto;
import dev.com.application.usecases.LoginUseCase;
import dev.com.domain.entities.User;
import dev.com.application.usecases.dtos.LoginRequestDto;
import dev.com.infrastructure.data.repositories.dynamodb.UserRepositoryImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

@QuarkusTest
public class LoginUseCaseTest {

    @Inject
    LoginUseCase loginUseCase;

    @InjectMock
    UserRepositoryImpl repository;

    @Test
    public void whenUserWasNotFoundThrows(){
        Mockito.when(repository.get(Mockito.any())).thenReturn(null);
        Assertions.assertThrows(RuntimeException.class, () -> {loginUseCase.execute(Mockito.any());});
    }


    @Test
    public void whenEmailAndPasswordIsCorrectReturnsToken(){
        User mockedUser = new User("dummy", "dummy@mail.com", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92");
        LoginRequestDto mockedRequest = new LoginRequestDto("dummy@mail.com","123456");
        Mockito.when(repository.get(Mockito.any())).thenReturn(mockedUser);
        LoginResponseDto response = loginUseCase.execute(mockedRequest.toUser());
        Assertions.assertTrue(response.getToken().length() > 0);
        Assertions.assertTrue(response.getRefreshToken().length() > 0);
    }
}
