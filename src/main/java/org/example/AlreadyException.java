package org.example;

public class AlreadyException extends RuntimeException {
    public AlreadyException() {
        super("Поздравляю  , вы прошли игру");
    }
}