package com.novabank.service.decorator;

import com.novabank.model.Cuenta;
import java.util.List;

public interface CuentaServiceInterface {
    Cuenta crearCuenta(Long clienteId);
    Cuenta buscarPorNumeroCuenta(String numeroCuenta);
    List<Cuenta> listarCuentasPorCliente(Long clienteId);
    Cuenta ingresar(String numeroCuenta, double cantidad);
    Cuenta retirar(String numeroCuenta, double cantidad);
    void transferir(String origen, String destino, double cantidad);
}