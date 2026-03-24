package main.java.com.novabank.service;

import main.java.com.novabank.exception.ClienteNoEncontradoException;
import main.java.com.novabank.exception.CuentaNoEncontrada;
import main.java.com.novabank.model.Cliente;
import main.java.com.novabank.model.Cuenta;
import main.java.com.novabank.model.TipoMovimiento;
import main.java.com.novabank.repository.CuentaRepository;

import java.util.ArrayList;
import java.util.List;

public class CuentaService {
    private CuentaRepository cuentaRepository;
    private ClienteService clienteService;
    private static long contadorCuentas = 1;


    public CuentaService(CuentaRepository cuentaRepository, ClienteService clienteService) {
        this.cuentaRepository = cuentaRepository;
        this.clienteService = clienteService;
    }

    public Cuenta crearCuenta(Long clienteId) {

        Cliente cliente = clienteService.encontrarPorId(clienteId);

        if (cliente == null) {
            throw new ClienteNoEncontradoException("El cliente no se ha encontrado.");
        }

        String numeroSecuencial = String.format("%012d", contadorCuentas++);
        String numeroCuenta = "ES91210000" + numeroSecuencial;
        Cuenta cuenta = new Cuenta(cliente, numeroCuenta);
        cuentaRepository.guardar(cuenta);
        return cuenta;
    }
    public Cuenta buscarPorNumeroCuenta(String numeroCuenta){
        Cuenta cuenta = cuentaRepository.buscarPorNumeroCuenta(numeroCuenta);
        if (cuenta == null){
            throw new CuentaNoEncontrada("La cuenta que buscas no se ha encontrado.");
        }else{
            return cuenta;
        }

    }
    public List<Cuenta> listarCuentasPorCliente(Long clienteId) {

        Cliente cliente = clienteService.encontrarPorId(clienteId);

        if (cliente == null) {
            throw new ClienteNoEncontradoException("Cliente no encontrado.");
        }

        return cuentaRepository.buscarPorClienteId(clienteId);
    }

    public void transferir(String numeroOrigen, String numeroDestino, double cantidad) {

        Cuenta origen = buscarPorNumeroCuenta(numeroOrigen);
        Cuenta destino = buscarPorNumeroCuenta(numeroDestino);

        if (origen.equals(destino)) {
            throw new IllegalArgumentException("No se puede transferir a la misma cuenta.");
        }
        boolean dineroMovido = false;

        try {

            origen.debitar(cantidad);
            destino.acreditar(cantidad);
            dineroMovido = true;

            origen.registrarMovimiento(TipoMovimiento.TRANSFERENCIA_SALIENTE,cantidad);
            destino.registrarMovimiento(TipoMovimiento.TRANSFERENCIA_ENTRANTE,cantidad);

        } catch (Exception e) {

            if (dineroMovido) {
                origen.acreditar(cantidad);
                destino.debitar(cantidad);
            }

            throw new RuntimeException("Error en la transferencia. Operación revertida.");
        }


    }
    public Cuenta ingresar(String numeroCuenta, double cantidad) {

        Cuenta cuenta = buscarPorNumeroCuenta(numeroCuenta);

        cuenta.depositoDinero(cantidad);
        cuenta.registrarMovimiento(TipoMovimiento.DEPOSITO, cantidad);

        return cuenta;
    }


    public void retirar(String numeroCuenta, double cantidad) {
        Cuenta cuenta = buscarPorNumeroCuenta(numeroCuenta);
        cuenta.retirarDinero(cantidad);
    }


}
