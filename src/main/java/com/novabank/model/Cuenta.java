package com.novabank.model;

import java.time.LocalDateTime;

public class Cuenta {

    private Long id;
    private String numeroCuenta;
    private Long titularId;
    private double saldo;
    private LocalDateTime fechaCreacion;

    // Constructor vacío (requerido por JDBC)
    public Cuenta() {}

    // Constructor para CUENTAS NUEVAS (sin id)
    public Cuenta(String numeroCuenta, Long titularId) {
        this.numeroCuenta = numeroCuenta;
        this.titularId = titularId;
        this.saldo = 0;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Constructor para CUENTAS QUE VIENEN DE LA BD
    public Cuenta(Long id, String numeroCuenta, Long titularId, double saldo, LocalDateTime fechaCreacion) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.titularId = titularId;
        this.saldo = saldo;
        this.fechaCreacion = fechaCreacion;
    }

    // GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public Long getTitularId() { return titularId; }
    public void setTitularId(Long titularId) { this.titularId = titularId; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}