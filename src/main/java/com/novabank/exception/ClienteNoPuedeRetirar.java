package com.novabank.exception;

public class ClienteNoPuedeRetirar extends RuntimeException {
    public ClienteNoPuedeRetirar(String message) {
        super(message);
    }
}
