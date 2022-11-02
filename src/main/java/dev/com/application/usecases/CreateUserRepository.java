package dev.com.application.usecases;

public interface CreateUserRepository {
    <T> void insert(T input);
}
