package com.novabank.exception;

public class CuentaNoEncontrada extends RuntimeException {
    public CuentaNoEncontrada(String message) {
        super(message);
    }
}
