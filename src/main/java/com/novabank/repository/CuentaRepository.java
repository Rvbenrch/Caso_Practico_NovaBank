package com.novabank.repository;

import com.novabank.model.Cuenta;
import java.util.List;
import java.util.Optional;

public interface CuentaRepository {

    Cuenta guardar(Cuenta cuenta);

    Optional<Cuenta> buscarPorId(Long id);

    Optional<Cuenta> buscarPorNumero(String numeroCuenta);

    List<Cuenta> listarPorCliente(Long clienteId);

    void actualizarSaldo(Long cuentaId, double nuevoSaldo);
}