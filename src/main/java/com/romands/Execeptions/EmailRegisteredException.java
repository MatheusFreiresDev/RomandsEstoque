package com.romands.Execeptions;

public class EmailRegisteredException extends RuntimeException{
    public EmailRegisteredException() {
        super("Email já registrado.");
    }
    public EmailRegisteredException(String string) {
        super(string);
    }
}
