package com.novabank.repository;

import com.novabank.model.Movimiento;
import java.time.LocalDate;
import java.util.List;

public interface MovimientoRepository {

    Movimiento guardar(Movimiento movimiento);

    List<Movimiento> listarPorCuenta(Long cuentaId);

    List<Movimiento> listarPorCuentaYRango(Long cuentaId, LocalDate inicio, LocalDate fin);
}