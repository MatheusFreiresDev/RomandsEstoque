package com.romands.Execeptions;

public class InvalidQuantityException extends RuntimeException {
    public InvalidQuantityException() {
        super("Quantidade inválida.");
    }
    public InvalidQuantityException(String message) {
        super(message);
    }
}
