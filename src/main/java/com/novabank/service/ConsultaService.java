package com.novabank.service;

import com.novabank.exception.CuentaNoEncontrada;
import com.novabank.model.Cuenta;
import com.novabank.model.Movimiento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ConsultaService {

    private CuentaService cuentaService;

    public ConsultaService(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    public double consultarSaldo(String numeroCuenta) {
        Cuenta cuenta = cuentaService.buscarPorNumeroCuenta(numeroCuenta);
        return cuenta.getSaldo();
    }


    public List<Movimiento> obtenerHistorial(String numeroCuenta) {
        Cuenta cuenta = cuentaService.buscarPorNumeroCuenta(numeroCuenta);

        return cuenta.getMovimientos()
                .stream()
                .sorted(Comparator.comparing(Movimiento::getFecha).reversed())
                .collect(Collectors.toList());
    }

    public List<Movimiento> obtenerMovimientosPorRango(String numeroCuenta,
                                                       LocalDate fechaInicio,
                                                       LocalDate fechaFin) {

        Cuenta cuenta = cuentaService.buscarPorNumeroCuenta(numeroCuenta);

        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        return cuenta.getMovimientos()
                .stream()
                .filter(m -> !m.getFecha().isBefore(inicio)
                        && !m.getFecha().isAfter(fin))
                .sorted(Comparator.comparing(Movimiento::getFecha).reversed())
                .collect(Collectors.toList());
    }
}