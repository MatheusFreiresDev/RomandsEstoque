package com.romands.Execeptions;

public class TransfrenciaNotFoundedException extends RuntimeException{
    public TransfrenciaNotFoundedException() {
        super("Transfêrencia nao encontrada.");
    }
    public TransfrenciaNotFoundedException(String string) {
        super(string);
    }

}
