package dev.com.application;

public interface Repository<T> {

    void insert(T input);

    T get(String key);

    void update(T input);

    void delete(T input);
}
