package main.java.com.novabank.model;

import main.java.com.novabank.exception.ClienteNoPuedeDepositar;
import main.java.com.novabank.exception.ClienteNoPuedeRetirar;
import main.java.com.novabank.service.ClienteService;
import main.java.com.novabank.service.CuentaService;

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
        this.saldo = 0;
    }

   public void depositoDinero(double cantidad){
        if(cantidad<=0){
            throw new ClienteNoPuedeDepositar("La cantidad de retiro no es correcta");
        }
        else{
            this.saldo +=cantidad;
        }
   }

   public void retirarDinero(double retiro){
        if(this.saldo<retiro){
            throw new ClienteNoPuedeRetirar("El saldo en la cuenta: " + this.saldo + " El importe que quieres retirar es: " + retiro + "\nIntentalo de nuevo con un importe válido");
        }
        if (retiro<=0){
            throw new ClienteNoPuedeRetirar("El importe a retirar no es válido: " + retiro);
        }
        else{
            this.saldo -= retiro;
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
}

