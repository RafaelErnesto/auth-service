package dev.com.application;

public interface Repository<T> {

    void insert(T input);

    <I> T get(I key);
}
