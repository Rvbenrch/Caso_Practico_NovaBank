package com.novabank.repository;

import com.novabank.model.Cuenta;

import java.util.*;

public class CuentaRepository {

    private Map<String, Cuenta> cuentas = new HashMap<>();

    public void guardar(Cuenta cuenta) {
        cuentas.put(cuenta.getNumeroCuenta(), cuenta);
    }

    public Cuenta buscarPorNumeroCuenta(String numeroCuenta) {
        return cuentas.get(numeroCuenta);
    }

    public List<Cuenta> listarCuentas() {
        return new ArrayList<>(cuentas.values());
    }

    public List<Cuenta> buscarPorClienteId(Long clienteId) {
        List<Cuenta> resultado = new ArrayList<>();

        for (Cuenta cuenta : cuentas.values()) {
            if (cuenta.getTitular().getId().equals(clienteId)) {
                resultado.add(cuenta);
            }
        }

        return resultado;
    }
}