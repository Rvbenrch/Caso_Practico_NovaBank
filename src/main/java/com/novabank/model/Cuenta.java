package main.java.com.novabank.model;

import main.java.com.novabank.exception.ClienteNoPuedeDepositar;
import main.java.com.novabank.exception.ClienteNoPuedeRetirar;
import main.java.com.novabank.service.ClienteService;
import main.java.com.novabank.service.CuentaService;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import static main.java.com.novabank.model.TipoMovimiento.*;

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

   public void depositoDinero(double cantidad){
        if(cantidad<=0){
            throw new ClienteNoPuedeDepositar("La cantidad de retiro no es correcta");
        }
        else{
            Movimiento mov = new Movimiento(DEPOSITO,cantidad);
            movimientos.add(mov);
            this.saldo +=cantidad;
        }
   }

   public void retirarDinero(double retiro){
       if (retiro<=0){
           throw new ClienteNoPuedeRetirar("El importe a retirar no es válido: " + retiro);
       }
        if(this.saldo<retiro){
            throw new ClienteNoPuedeRetirar(" Saldo insuficiente.\nSaldo disponible: " + this.saldo +
                    "\nEl importe que quiere retirar es: " + retiro +
                    "\nIntentalo de nuevo con un importe válido");
        }

        else{
            this.saldo -= retiro;
            Movimiento mov = new Movimiento(RETIRO,retiro);
            movimientos.add(mov);
        }
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
    public List<Movimiento> getMovimientos() {
        return movimientos;
    }



    // Asegurar transacción.
    public void debitar(double cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }
        if (this.saldo < cantidad) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        this.saldo -= cantidad;
    }

    public void acreditar(double cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }
        this.saldo += cantidad;
    }



   // Realizar movimientos:
    public void registrarMovimiento(TipoMovimiento tipo, double cantidad) {
        Movimiento mov = new Movimiento(tipo, cantidad);
        movimientos.add(mov);
    }

}

