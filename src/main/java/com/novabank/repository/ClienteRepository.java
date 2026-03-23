package com.novabank.repository;

import com.novabank.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {
    private List<Cliente> clientes = new ArrayList<>();

    public void guardar(Cliente cliente) {
        clientes.add(cliente);
    }
    public Cliente buscarPorDni(String dni) {

        for (Cliente cliente : clientes) {

            if (cliente.getDni().equals(dni)) {
                return cliente;
            }

        }

        return null;
    }
    public Cliente buscarPorEmail(String email) {
    for(Cliente cliente:clientes){
        if(cliente.getEmail().equals(email)){
            return cliente;
        }
    }
        return null;
    }

    public Cliente buscarPorTelefono(String telefono) {
        for(Cliente cliente:clientes){
            if(cliente.getTelefono().equals(telefono)){
                return cliente;
            }
        }
        return null;
    }


}

