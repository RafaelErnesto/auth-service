package dev.com.application.exceptions;

public class CreateConfirmationHashException extends RuntimeException{
    public CreateConfirmationHashException(String message) {
        super(message);
    }
}
