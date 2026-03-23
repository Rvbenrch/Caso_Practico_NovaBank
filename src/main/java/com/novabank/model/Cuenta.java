package main.java.com.novabank.model;

import java.time.LocalDateTime;

public class Cuenta {

    private String numeroCuenta;
    private Cliente titular;
    private double saldo;
    private LocalDateTime fechaCreacion;

    public Cuenta(Cliente titular, String numeroCuenta) {
        this.titular = titular;
        this.numeroCuenta = numeroCuenta;
        this.fechaCreacion = LocalDateTime.now();
        this.saldo =0;
    }

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
}

