package dev.com.application.usecases;

public interface GetConfirmationHashRepository {
    ConfirmationHash get(String hashValue);
}
