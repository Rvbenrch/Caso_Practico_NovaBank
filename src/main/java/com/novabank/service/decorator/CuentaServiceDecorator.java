package com.novabank.service.decorator;

import com.novabank.model.Cuenta;
import com.novabank.service.CuentaServiceInterface;

import java.util.List;

public abstract class CuentaServiceDecorator implements CuentaServiceInterface {

    protected final CuentaServiceInterface wrapped;

    public CuentaServiceDecorator(CuentaServiceInterface wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public Cuenta crearCuenta(Long clienteId) {
        return wrapped.crearCuenta(clienteId);
    }

    @Override
    public Cuenta buscarPorNumeroCuenta(String numeroCuenta) {
        return wrapped.buscarPorNumeroCuenta(numeroCuenta);
    }

    @Override
    public List<Cuenta> listarCuentasPorCliente(Long clienteId) {
        return wrapped.listarCuentasPorCliente(clienteId);
    }

    @Override
    public Cuenta ingresar(String numeroCuenta, double cantidad) {
        return wrapped.ingresar(numeroCuenta, cantidad);
    }

    @Override
    public Cuenta retirar(String numeroCuenta, double cantidad) {
        return wrapped.retirar(numeroCuenta, cantidad);
    }

    @Override
    public void transferir(String origen, String destino, double cantidad) {
        wrapped.transferir(origen, destino, cantidad);
    }
}