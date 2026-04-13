package com.novabank.service.strategy;

import com.novabank.model.Cuenta;

public class OperacionContext {

    private OperacionStrategy strategy;

    public void setStrategy(OperacionStrategy strategy) {
        this.strategy = strategy;
    }

    public void ejecutar(Cuenta cuenta, double cantidad) {
        strategy.ejecutar(cuenta, cantidad);
    }
}