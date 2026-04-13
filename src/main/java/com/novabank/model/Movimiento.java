package com.novabank.model;

import java.time.LocalDateTime;

public class Movimiento {

    private Long id;
    private Long cuentaId;
    private TipoMovimiento tipo;
    private double importe;
    private LocalDateTime fecha;

    // Constructor vacío (requerido por JDBC)
    public Movimiento() {}

    // Constructor para movimientos NUEVOS
    public Movimiento(Long cuentaId, TipoMovimiento tipo, double importe) {
        this.cuentaId = cuentaId;
        this.tipo = tipo;
        this.importe = importe;
        this.fecha = LocalDateTime.now();
    }

    // Constructor para movimientos que vienen de la BD
    public Movimiento(Long id, Long cuentaId, TipoMovimiento tipo, double importe, LocalDateTime fecha) {
        this.id = id;
        this.cuentaId = cuentaId;
        this.tipo = tipo;
        this.importe = importe;
        this.fecha = fecha;
    }

    // GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCuentaId() { return cuentaId; }
    public void setCuentaId(Long cuentaId) { this.cuentaId = cuentaId; }

    public TipoMovimiento getTipo() { return tipo; }
    public void setTipo(TipoMovimiento tipo) { this.tipo = tipo; }

    public double getImporte() { return importe; }
    public void setImporte(double importe) { this.importe = importe; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}