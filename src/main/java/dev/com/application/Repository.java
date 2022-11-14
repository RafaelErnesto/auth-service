package dev.com.application;

public interface Repository {

    <T> void insert(T input);

    <I,O> O get(I key);
}
