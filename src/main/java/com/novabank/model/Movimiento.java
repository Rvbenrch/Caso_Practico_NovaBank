package main.java.com.novabank.model;

import java.time.LocalDateTime;

public class Movimiento {

    private TipoMovimiento tipo;
    private double importe;
    private LocalDateTime fecha;

    public Movimiento(TipoMovimiento tipo, double importe) {
        this.tipo = tipo;
        this.importe = importe;
        this.fecha = LocalDateTime.now();
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }
    public double getImporte() {
        return importe;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
}
