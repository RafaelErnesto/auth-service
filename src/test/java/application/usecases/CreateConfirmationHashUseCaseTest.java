package application.usecases;

import dev.com.application.usecases.CreateConfirmationHashUseCase;
import dev.com.domain.entities.ConfirmationHash;
import dev.com.domain.entities.User;
import dev.com.infrastructure.data.repositories.dynamodb.ConfirmationHashRepositoryImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

@QuarkusTest
public class CreateConfirmationHashUseCaseTest {

    @InjectMock
    ConfirmationHashRepositoryImpl confirmationHashRepository;

    @Inject
    CreateConfirmationHashUseCase sut;

    @Test
    void shouldReturnConfirmationHashWhenOk(){
        User mockedUser = new User("dummy", "dummy@mail.com","123456");
        ConfirmationHash returnedHash = sut.execute(mockedUser);
        Assertions.assertTrue(returnedHash.getValue().length() > 0);
        Mockito.verify(confirmationHashRepository).insert(returnedHash);
    }
}
