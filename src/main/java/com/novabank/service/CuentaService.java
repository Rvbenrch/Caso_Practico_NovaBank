package com.novabank.service;

import com.novabank.config.DatabaseConnectionManager;
import com.novabank.exception.ClienteNoEncontradoException;
import com.novabank.exception.CuentaNoEncontrada;
import com.novabank.model.Cliente;
import com.novabank.model.Cuenta;
import com.novabank.model.Movimiento;
import com.novabank.model.TipoMovimiento;
import com.novabank.repository.CuentaRepository;
import com.novabank.repository.MovimientoRepository;
import com.novabank.service.strategy.IngresoStrategy;
import com.novabank.service.strategy.OperacionContext;
import com.novabank.service.strategy.RetiradaStrategy;
import com.novabank.service.strategy.TransferenciaStrategy;

import java.sql.Connection;
import java.sql.SQLException;
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

        // Strategy
        OperacionContext ctx = new OperacionContext();
        ctx.setStrategy(new IngresoStrategy());
        ctx.ejecutar(cuenta, cantidad);

        // Persistencia
        cuentaRepository.actualizarSaldo(cuenta.getId(), cuenta.getSaldo());

        movimientoRepository.guardar(
                new Movimiento(cuenta.getId(), TipoMovimiento.DEPOSITO, cantidad)
        );

        return cuenta;
    }

    public Cuenta retirar(String numeroCuenta, double cantidad) {

        Cuenta cuenta = buscarPorNumeroCuenta(numeroCuenta);

        // Strategy
        OperacionContext ctx = new OperacionContext();
        ctx.setStrategy(new RetiradaStrategy());
        ctx.ejecutar(cuenta, cantidad);

        // Persistencia
        cuentaRepository.actualizarSaldo(cuenta.getId(), cuenta.getSaldo());

        movimientoRepository.guardar(
                new Movimiento(cuenta.getId(), TipoMovimiento.RETIRO, cantidad)
        );

        return cuenta;
    }

    public void transferir(String origen, String destino, double cantidad) {

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection()) {

            conn.setAutoCommit(false); // Inicio de transacción

            try {
                Cuenta cuentaOrigen = cuentaRepository
                        .buscarPorNumero(origen, conn)
                        .orElseThrow(() -> new CuentaNoEncontrada("Cuenta origen no encontrada"));

                Cuenta cuentaDestino = cuentaRepository
                        .buscarPorNumero(destino, conn)
                        .orElseThrow(() -> new CuentaNoEncontrada("Cuenta destino no encontrada"));

                // Strategy para ORIGEN
                OperacionContext ctx = new OperacionContext();
                ctx.setStrategy(new TransferenciaStrategy());
                ctx.ejecutar(cuentaOrigen, cantidad);

                // Strategy para DESTINO (es un ingreso)
                ctx.setStrategy(new IngresoStrategy());
                ctx.ejecutar(cuentaDestino, cantidad);

                // Persistencia transaccional
                cuentaRepository.actualizarSaldo(cuentaOrigen.getId(), cuentaOrigen.getSaldo(), conn);
                cuentaRepository.actualizarSaldo(cuentaDestino.getId(), cuentaDestino.getSaldo(), conn);

                movimientoRepository.guardar(
                        new Movimiento(cuentaOrigen.getId(), TipoMovimiento.TRANSFERENCIA_SALIENTE, cantidad),
                        conn
                );

                movimientoRepository.guardar(
                        new Movimiento(cuentaDestino.getId(), TipoMovimiento.TRANSFERENCIA_ENTRANTE, cantidad),
                        conn
                );

                conn.commit();

            } catch (Exception e) {
                conn.rollback();
                throw new RuntimeException("Error en la transferencia: " + e.getMessage(), e);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error de conexión", e);
        }
    }

    private String generarNumeroCuenta() {
        long timestamp = System.currentTimeMillis();
        return "ES91" + timestamp;
    }
}