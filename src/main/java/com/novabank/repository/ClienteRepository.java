package com.novabank.repository;

import com.novabank.model.Cliente;

import java.util.*;

public class ClienteRepository {

    private Map<String, Cliente> clientesPorDni = new HashMap<>();
    private Map<String, Cliente> clientesPorEmail = new HashMap<>();
    private Map<String, Cliente> clientesPorTelefono = new HashMap<>();
    private Map<Long, Cliente> clientesPorId = new HashMap<>();

    public void guardar(Cliente cliente) {
        clientesPorDni.put(cliente.getDni(), cliente);
        clientesPorEmail.put(cliente.getEmail(), cliente);
        clientesPorTelefono.put(cliente.getTelefono(), cliente);
        clientesPorId.put(cliente.getId(), cliente);
    }

    public Cliente buscarPorDni(String dni) {
        return clientesPorDni.get(dni);
    }

    public Cliente buscarPorEmail(String email) {
        return clientesPorEmail.get(email);
    }

    public Cliente buscarPorTelefono(String telefono) {
        return clientesPorTelefono.get(telefono);
    }

    public Cliente buscarPorId(long id) {
        return clientesPorId.get(id);
    }

    public List<Cliente> buscarTodos() {
        return new ArrayList<>(clientesPorDni.values());
    }
}