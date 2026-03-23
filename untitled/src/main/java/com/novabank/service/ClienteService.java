package com.novabank.service;

import com.novabank.exception.ClienteDuplicadoException;
import com.novabank.model.Cliente;
import com.novabank.repository.ClienteRepository;

public class ClienteService {

    private ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }
    public void crearCliente(String nombre, String apellidos, String dni, String email, String telefono){
        if (repository.buscarPorDni(dni) != null  ){
            throw new ClienteDuplicadoException("Ya existe un cliente con el DNI: " + dni);
        }
        Cliente cliente = new Cliente(nombre, apellidos, dni, email, telefono);
        repository.guardar(cliente);


    }


}
