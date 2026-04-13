package com.novabank.service;

import com.novabank.exception.CuentaNoEncontrada;
import com.novabank.model.Cuenta;
import com.novabank.model.Movimiento;
import com.novabank.repository.MovimientoRepository;

import java.time.LocalDate;
import java.util.List;

public class ConsultaService {

    private final CuentaService cuentaService;
    private final MovimientoRepository movimientoRepository;

    public ConsultaService(CuentaService cuentaService, MovimientoRepository movimientoRepository) {
        this.cuentaService = cuentaService;
        this.movimientoRepository = movimientoRepository;
    }

    public double consultarSaldo(String numeroCuenta) {
        Cuenta cuenta = cuentaService.buscarPorNumeroCuenta(numeroCuenta);
        return cuenta.getSaldo();
    }

    public List<Movimiento> obtenerHistorial(String numeroCuenta) {
        Cuenta cuenta = cuentaService.buscarPorNumeroCuenta(numeroCuenta);
        return movimientoRepository.listarPorCuenta(cuenta.getId());
    }

    public List<Movimiento> obtenerMovimientosPorRango(String numeroCuenta,
                                                       LocalDate fechaInicio,
                                                       LocalDate fechaFin) {

        Cuenta cuenta = cuentaService.buscarPorNumeroCuenta(numeroCuenta);
        return movimientoRepository.listarPorCuentaYRango(cuenta.getId(), fechaInicio, fechaFin);
    }
}