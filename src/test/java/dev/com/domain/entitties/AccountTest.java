package dev.com.domain.entitties;

import dev.com.domain.entities.Account;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
public class AccountTest {

    @Test
    void hashUserPasswordValidationTrueTest(){
        Account account = new Account("Dummy", "dummy@mail.com", "123456", "dummy");
        Assertions.assertTrue(account.validatePassword("123456"),"User password validation failed");
    }

    @Test
    void hashUserPasswordValidationFalseTest(){
        Account account = new Account("Dummy", "dummy@mail.com", "123456", "dummy");
        Assertions.assertFalse(account.validatePassword("12345600"),"User password validation failed");
    }

    @Test
    void hashUserPasswordThrowsTest() {
        Account mockedAccount = Mockito.mock(Account.class);
        Mockito.when(mockedAccount.validatePassword(any())).thenThrow();
        Assertions.assertThrows(RuntimeException.class, () -> mockedAccount.validatePassword("123456"));
    }
}
