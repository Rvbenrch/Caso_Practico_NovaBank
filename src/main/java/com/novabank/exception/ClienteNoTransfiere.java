package com.novabank.exception;

public class ClienteNoTransfiere extends RuntimeException {
    public ClienteNoTransfiere(String message) {
        super(message);
    }
}
