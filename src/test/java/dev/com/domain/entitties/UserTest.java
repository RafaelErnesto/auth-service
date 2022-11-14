package dev.com.domain.entitties;

import dev.com.domain.entities.User;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
public class UserTest {

    @Test
    void hashUserPasswordValidationTrueTest(){
        User user = new User("Dummy", "dummy@mail.com", "123456");
        Assertions.assertTrue(user.validatePassword("123456"),"User password validation failed");
    }

    @Test
    void hashUserPasswordValidationFalseTest(){
        User user = new User("Dummy", "dummy@mail.com", "123456");
        Assertions.assertFalse(user.validatePassword("12345600"),"User password validation failed");
    }

    @Test
    void hashUserPasswordThrowsTest() {
        User mockedUser = Mockito.mock(User.class);
        Mockito.when(mockedUser.validatePassword(any())).thenThrow();
        Assertions.assertThrows(RuntimeException.class, () -> mockedUser.validatePassword("123456"));
    }
}
