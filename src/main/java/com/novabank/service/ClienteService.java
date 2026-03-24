package main.java.com.novabank.service;

import main.java.com.novabank.exception.ClienteDuplicadoException;
import main.java.com.novabank.model.Cliente;
import main.java.com.novabank.repository.ClienteRepository;

import java.util.List;

public class ClienteService {

    private ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {

        this.repository = repository;
    }
    public Cliente crearCliente(String nombre, String apellidos, String dni, String email, String telefono){

        if (repository.buscarPorDni(dni) != null){
            throw new ClienteDuplicadoException("Ya existe un cliente con el DNI: " + dni);
        }

        if (repository.buscarPorEmail(email) != null){
            throw new ClienteDuplicadoException("Ya existe un cliente con el email: " + email);
        }

        if (repository.buscarPorTelefono(telefono) != null){
            throw new ClienteDuplicadoException("Ya existe un cliente con el teléfono: " + telefono);
        }

        if (!email.contains("@") || !email.contains(".")){
            throw new IllegalArgumentException("El email no tiene un formato válido. \n El formato del email tiene que ser: ejemplo@dominio.com");
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
    public List<Cliente> listarClientes() {
        return repository.buscarTodos();
    }

    public Cliente encontrarPorEmail(String email){
        return repository.buscarPorEmail(email);
    }


}
