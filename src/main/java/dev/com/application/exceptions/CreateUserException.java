package dev.com.application.exceptions;

public class CreateUserException extends RuntimeException{
    public CreateUserException(String message) {
        super(message);
    }
}
