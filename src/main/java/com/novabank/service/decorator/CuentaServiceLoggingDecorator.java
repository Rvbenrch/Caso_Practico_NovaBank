package com.novabank.service.decorator;

import com.novabank.model.Cuenta;
import com.novabank.service.CuentaServiceInterface;

public class CuentaServiceLoggingDecorator extends CuentaServiceDecorator {

    public CuentaServiceLoggingDecorator(CuentaServiceInterface wrapped) {
        super(wrapped);
    }

    @Override
    public Cuenta ingresar(String numeroCuenta, double cantidad) {
        System.out.println("[LOG] Ingreso en cuenta " + numeroCuenta + " por " + cantidad + "€");
        return super.ingresar(numeroCuenta, cantidad);
    }

    @Override
    public Cuenta retirar(String numeroCuenta, double cantidad) {
        System.out.println("[LOG] Retirada en cuenta " + numeroCuenta + " por " + cantidad + "€");
        return super.retirar(numeroCuenta, cantidad);
    }

    @Override
    public void transferir(String origen, String destino, double cantidad) {
        System.out.println("[LOG] Transferencia desde " + origen + " hacia " + destino + " por " + cantidad + "€");
        super.transferir(origen, destino, cantidad);
        System.out.println("[LOG] Transferencia completada.");
    }
}