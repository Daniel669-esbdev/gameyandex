package org.example;

public class NoException extends RuntimeException {
    public NoException() {
        super("Слово не в словаре");
    }
}