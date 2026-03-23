package main.java.com.novabank.service;

import main.java.com.novabank.exception.ClienteDuplicadoException;
import main.java.com.novabank.model.Cliente;
import main.java.com.novabank.repository.ClienteRepository;

public class ClienteService {

    private ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }
    public Cliente crearCliente(String nombre, String apellidos, String dni, String email, String telefono){
        if (repository.buscarPorDni(dni) != null  ){
            throw new ClienteDuplicadoException("Ya existe un cliente con el DNI: " + dni);
        }
        Cliente cliente = new Cliente(nombre, apellidos, dni, email, telefono);
        repository.guardar(cliente);
        return cliente;


    }
    public Cliente encontrarPorDni(String dni){
        return repository.buscarPorDni(dni);
    }

    public Cliente encontrarPorId(long id){
        return repository.buscarPorId(id);
    }


}
