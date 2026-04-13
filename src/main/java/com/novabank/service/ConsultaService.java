package com.novabank.service;

import com.novabank.model.Movimiento;
import com.novabank.repository.MovimientoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ConsultaService {

    private final CuentaServiceInterface cuentaService;
    private final MovimientoRepository movimientoRepository;

    public ConsultaService(CuentaServiceInterface cuentaService,
                           MovimientoRepository movimientoRepository) {
        this.cuentaService = cuentaService;
        this.movimientoRepository = movimientoRepository;
    }

    public double consultarSaldo(String numeroCuenta) {
        return cuentaService.buscarPorNumeroCuenta(numeroCuenta).getSaldo();
    }

    public List<Movimiento> obtenerHistorial(String numeroCuenta) {
        Long cuentaId = cuentaService.buscarPorNumeroCuenta(numeroCuenta).getId();
        return movimientoRepository.listarPorCuenta(cuentaId);
    }
    public List<Movimiento> obtenerMovimientosPorRango(String numeroCuenta,
                                                       LocalDate fechaInicio,
                                                       LocalDate fechaFin) {

        Long cuentaId = cuentaService.buscarPorNumeroCuenta(numeroCuenta).getId();

        return movimientoRepository.listarPorCuentaYRango(cuentaId, fechaInicio, fechaFin);
    }
}