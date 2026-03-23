package main.java.com.novabank.service;

import main.java.com.novabank.exception.ClienteNoEncontradoException;
import main.java.com.novabank.model.Cliente;
import main.java.com.novabank.model.Cuenta;
import main.java.com.novabank.repository.CuentaRepository;

public class CuentaService {
    private CuentaRepository cuentaRepository;
    private ClienteService clienteService;

    public CuentaService(CuentaRepository cuentaRepository, ClienteService clienteService) {
        this.cuentaRepository = cuentaRepository;
        this.clienteService = clienteService;
    }

    public Cuenta crearCuenta(Long clienteId) {

        Cliente cliente = clienteService.encontrarPorId(clienteId);

        if (cliente == null) {
            throw new ClienteNoEncontradoException("El cliente no se ha encontrado.");
        }

        String numeroCuenta = "ES" + System.currentTimeMillis(); // Devuelve el tiempo en milisegundos, con esto genero un identificador único.
        Cuenta cuenta = new Cuenta(cliente, numeroCuenta);
        cuentaRepository.guardar(cuenta);
        return cuenta;
    }
}
