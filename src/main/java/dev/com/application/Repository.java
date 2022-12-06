package dev.com.application;

public interface Repository<T> {

    void insert(T input);

    T get(String key);
}
