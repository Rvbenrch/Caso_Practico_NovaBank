package com.novabank.repository;

import com.novabank.model.Cuenta;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface CuentaRepository {

    Cuenta guardar(Cuenta cuenta);

    Optional<Cuenta> buscarPorId(Long id);

    Optional<Cuenta> buscarPorNumero(String numeroCuenta);

    List<Cuenta> listarPorCliente(Long clienteId);

    Optional<Cuenta> buscarPorNumero(String numeroCuenta, Connection conn);

    void actualizarSaldo(Long cuentaId, double nuevoSaldo);

    void actualizarSaldo(Long cuentaId, double nuevoSaldo, Connection conn);
}