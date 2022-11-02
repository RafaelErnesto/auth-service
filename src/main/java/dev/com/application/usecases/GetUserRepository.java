package dev.com.application.usecases;

import dev.com.domain.entities.User;

public interface GetUserRepository {
    <T> User get(T input);
}
