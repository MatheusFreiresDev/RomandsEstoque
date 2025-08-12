package com.romands.Execeptions;

public class ProductNotFounded extends RuntimeException {
    public ProductNotFounded() {
        super("Produto não encontrado.");
    }
    public ProductNotFounded(String message) {
        super(message);
    }
}
