package com.novabank.service.strategy;

import com.novabank.model.Cuenta;

public class TransferenciaStrategy implements OperacionStrategy {

    @Override
    public void ejecutar(Cuenta cuenta, double cantidad) {
        if (cuenta.getSaldo() < cantidad) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }
        double nuevoSaldo = cuenta.getSaldo() - cantidad;
        cuenta.setSaldo(nuevoSaldo);
    }
}