package com.novabank.service;

import com.novabank.exception.ClienteNoEncontradoException;
import com.novabank.exception.CuentaNoEncontrada;
import com.novabank.model.Cliente;
import com.novabank.model.Cuenta;
import com.novabank.model.Movimiento;
import com.novabank.model.TipoMovimiento;
import com.novabank.repository.CuentaRepository;
import com.novabank.repository.MovimientoRepository;

import java.util.List;

public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteService clienteService;
    private final MovimientoRepository movimientoRepository;

    public CuentaService(CuentaRepository cuentaRepository,
                         ClienteService clienteService,
                         MovimientoRepository movimientoRepository) {

        this.cuentaRepository = cuentaRepository;
        this.clienteService = clienteService;
        this.movimientoRepository = movimientoRepository;
    }

    public Cuenta crearCuenta(Long clienteId) {

        Cliente cliente = clienteService.encontrarPorId(clienteId);

        if (cliente == null) {
            throw new ClienteNoEncontradoException("El cliente no se ha encontrado.");
        }

        String numeroCuenta = generarNumeroCuenta();

        Cuenta cuenta = new Cuenta(numeroCuenta, clienteId);

        return cuentaRepository.guardar(cuenta);
    }

    public Cuenta buscarPorNumeroCuenta(String numeroCuenta) {
        return cuentaRepository.buscarPorNumero(numeroCuenta)
                .orElseThrow(() -> new CuentaNoEncontrada("La cuenta que buscas no se ha encontrado."));
    }

    public List<Cuenta> listarCuentasPorCliente(Long clienteId) {

        clienteService.encontrarPorId(clienteId);

        return cuentaRepository.listarPorCliente(clienteId);
    }

    public Cuenta ingresar(String numeroCuenta, double cantidad) {

        Cuenta cuenta = buscarPorNumeroCuenta(numeroCuenta);

        double nuevoSaldo = cuenta.getSaldo() + cantidad;
        cuentaRepository.actualizarSaldo(cuenta.getId(), nuevoSaldo);
        cuenta.setSaldo(nuevoSaldo);

        Movimiento movimiento = new Movimiento(
                cuenta.getId(),
                TipoMovimiento.DEPOSITO,
                cantidad
        );

        movimientoRepository.guardar(movimiento);

        return cuenta;
    }

    public Cuenta retirar(String numeroCuenta, double cantidad) {

        Cuenta cuenta = buscarPorNumeroCuenta(numeroCuenta);

        if (cuenta.getSaldo() < cantidad) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }

        double nuevoSaldo = cuenta.getSaldo() - cantidad;
        cuentaRepository.actualizarSaldo(cuenta.getId(), nuevoSaldo);
        cuenta.setSaldo(nuevoSaldo);

        Movimiento movimiento = new Movimiento(
                cuenta.getId(),
                TipoMovimiento.RETIRO,
                cantidad
        );

        movimientoRepository.guardar(movimiento);

        return cuenta;
    }

    public void transferir(String origen, String destino, double cantidad) {

        Cuenta cuentaOrigen = buscarPorNumeroCuenta(origen);
        Cuenta cuentaDestino = buscarPorNumeroCuenta(destino);

        if (cuentaOrigen.getSaldo() < cantidad) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar la transferencia.");
        }

        double saldoOrigen = cuentaOrigen.getSaldo() - cantidad;
        double saldoDestino = cuentaDestino.getSaldo() + cantidad;

        cuentaRepository.actualizarSaldo(cuentaOrigen.getId(), saldoOrigen);
        cuentaRepository.actualizarSaldo(cuentaDestino.getId(), saldoDestino);

        cuentaOrigen.setSaldo(saldoOrigen);
        cuentaDestino.setSaldo(saldoDestino);

        movimientoRepository.guardar(
                new Movimiento(cuentaOrigen.getId(), TipoMovimiento.TRANSFERENCIA_SALIENTE, cantidad)
        );

        movimientoRepository.guardar(
                new Movimiento(cuentaDestino.getId(), TipoMovimiento.TRANSFERENCIA_ENTRANTE, cantidad)
        );
    }

    private String generarNumeroCuenta() {
        long timestamp = System.currentTimeMillis();
        return "ES91" + timestamp;
    }
}