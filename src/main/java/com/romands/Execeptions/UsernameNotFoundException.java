package com.romands.Execeptions;

public class UsernameNotFoundException extends RuntimeException {

    public UsernameNotFoundException() {
        super("Usuário não encontrado.");
    }
    public UsernameNotFoundException(String message) {
        super(message);
    }
}
