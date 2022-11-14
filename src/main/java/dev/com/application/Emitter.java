package dev.com.application;

public interface Emitter {
    <T> void execute(T payload);
}
