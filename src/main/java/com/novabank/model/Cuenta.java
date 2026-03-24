package com.novabank.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.novabank.model.TipoMovimiento.*;

public class Cuenta {

    private String numeroCuenta;
    private Cliente titular;
    private double saldo;
    private LocalDateTime fechaCreacion;
    private List<Movimiento> movimientos;

    public Cuenta(Cliente titular, String numeroCuenta) {
        this.titular = titular;
        this.numeroCuenta = numeroCuenta;
        this.fechaCreacion = LocalDateTime.now();
        this.saldo = 0;
        this.movimientos = new ArrayList<>();
    }


    // OPERACIONES DE NEGOCIO


    public void ingresar(double cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a ingresar debe ser mayor que 0");
        }

        this.saldo += cantidad;
        movimientos.add(new Movimiento(DEPOSITO, cantidad));
    }

    public void retirar(double cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a retirar debe ser mayor que 0");
        }

        if (this.saldo < cantidad) {
            throw new IllegalArgumentException(
                    "Saldo insuficiente. Disponible: " + this.saldo
            );
        }

        this.saldo -= cantidad;
        movimientos.add(new Movimiento(RETIRO, cantidad));
    }

    public void transferirA(Cuenta destino, double cantidad) {
        if (destino == null) {
            throw new IllegalArgumentException("La cuenta destino no puede ser null");
        }

        if (this.equals(destino)) {
            throw new IllegalArgumentException("No puedes transferir a la misma cuenta");
        }


        this.retirar(cantidad);


        destino.ingresar(cantidad);

        this.movimientos.add(new Movimiento(TRANSFERENCIA_SALIENTE, cantidad));
        destino.movimientos.add(new Movimiento(TRANSFERENCIA_ENTRANTE, cantidad));
    }


    // GETTERS


    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public Cliente getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }
}