package application.usecases;

import dev.com.application.usecases.CreateUserUseCase;
import dev.com.application.usecases.Repository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

@QuarkusTest
public class CreateUserUseCaseTest {

    @Inject
    CreateUserUseCase createUserUseCase;

    @InjectMock
    Repository userRepository;

    @Test
    void addUserThrowsWhenUserExists(){
        Account account = new Account("Joseph", "j@mail.com","123456","jh");
        Mockito.when(userRepository.get(account.getEmail())).thenReturn(account);
        Assertions.assertThrows(Exception.class, () -> createUserUseCase.execute(account.toUser()));
    }

    @Test
    void addUserInsertsWhenUserIsOK(){
        Account account = new Account("Joseph", "j@mail.com","123456","jh");
        Mockito.when(userRepository.get(account.getEmail())).thenReturn(null);
        createUserUseCase.execute(account.toUser());
        Mockito.verify(userRepository).insert(account);
    }
}
