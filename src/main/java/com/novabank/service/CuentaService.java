package main.java.com.novabank.service;

import main.java.com.novabank.exception.ClienteNoEncontradoException;
import main.java.com.novabank.exception.CuentaNoEncontrada;
import main.java.com.novabank.model.Cliente;
import main.java.com.novabank.model.Cuenta;
import main.java.com.novabank.repository.CuentaRepository;

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

}
