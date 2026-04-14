package com.novabank.factory;

import com.novabank.model.Movimiento;
import com.novabank.model.TipoMovimiento;

public class MovimientoFactory {

    public static Movimiento crearIngreso(Long cuentaId, double cantidad) {
        return new Movimiento(cuentaId, TipoMovimiento.DEPOSITO, cantidad);
    }

    public static Movimiento crearRetirada(Long cuentaId, double cantidad) {
        return new Movimiento(cuentaId, TipoMovimiento.RETIRO, cantidad);
    }

    public static Movimiento crearTransferenciaSaliente(Long cuentaId, double cantidad) {
        return new Movimiento(cuentaId, TipoMovimiento.TRANSFERENCIA_SALIENTE, cantidad);
    }

    public static Movimiento crearTransferenciaEntrante(Long cuentaId, double cantidad) {
        return new Movimiento(cuentaId, TipoMovimiento.TRANSFERENCIA_ENTRANTE, cantidad);
    }
}