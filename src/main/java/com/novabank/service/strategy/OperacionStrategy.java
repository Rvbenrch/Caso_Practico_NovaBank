package com.novabank.service.strategy;

import com.novabank.model.Cuenta;

public interface OperacionStrategy {
    void ejecutar(Cuenta cuenta, double cantidad);
}