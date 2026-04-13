package com.novabank.service.strategy;

import com.novabank.model.Cuenta;

public class IngresoStrategy implements OperacionStrategy {

    @Override
    public void ejecutar(Cuenta cuenta, double cantidad) {
        double nuevoSaldo = cuenta.getSaldo() + cantidad;
        cuenta.setSaldo(nuevoSaldo);
    }
}