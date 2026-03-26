package com.novabank.menus;

import com.novabank.model.Cliente;
import com.novabank.service.ClienteService;

import java.util.List;
import java.util.Scanner;

public class MenuCliente {

    private Scanner scanner;
    private ClienteService clienteService;

    public MenuCliente(Scanner scanner, ClienteService clienteService) {
        this.scanner = scanner;
        this.clienteService = clienteService;
    }

    public void mostrar() {
        int opcion;

        do {
            System.out.println("\n--- GESTION DE CLIENTES ---");
            System.out.println("1. Crear cliente");
            System.out.println("2. Buscar cliente");
            System.out.println("3. Listar clientes");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opcion: ");

            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    crearCliente();
                    break;
                case 2:
                    buscarCliente();
                    break;
                case 3:
                    listarClientes();
                    break;
                case 4:
                    System.out.println("Volviendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 4);
    }

    private void crearCliente() {
        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Apellidos: ");
            String apellidos = scanner.nextLine();

            System.out.print("DNI: ");
            String dni = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine();

            Cliente cliente = clienteService.crearCliente(nombre, apellidos, dni, email, telefono);

            System.out.println("\nCliente creado correctamente:");
            mostrarCliente(cliente);

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void buscarCliente() {
        System.out.println("1. Buscar por ID");
        System.out.println("2. Buscar por DNI");
        System.out.print("Seleccione una opción: ");

        int opcionBusqueda = Integer.parseInt(scanner.nextLine());

        try {
            Cliente cliente = null;

            if (opcionBusqueda == 1) {
                System.out.print("Introduzca el ID: ");
                long id = Long.parseLong(scanner.nextLine());
                cliente = clienteService.encontrarPorId(id);
            } else if (opcionBusqueda == 2) {
                System.out.print("Introduzca el DNI: ");
                String dni = scanner.nextLine();
                cliente = clienteService.encontrarPorDni(dni);
            } else {
                System.out.println("Opción inválida.");
                return;
            }

            if (cliente != null) {
                System.out.println();
                mostrarCliente(cliente);
            } else {
                System.out.println("Cliente no encontrado.");
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        System.out.println();
        System.out.printf("%-4s | %-18s | %-10s | %-25s | %-12s%n",
                "ID", "Nombre", "DNI", "Email", "Teléfono");
        System.out.println("-----|--------------------|------------|---------------------------|-------------");

        for (Cliente c : clientes) {
            mostrarCliente(c);
        }
    }

    private void mostrarCliente(Cliente c) {
        String nombreCompleto = c.getNombre() + " " + c.getApellidos();
        nombreCompleto = nombreCompleto.length() > 18 ? nombreCompleto.substring(0, 18) : nombreCompleto;
        String dni = c.getDni().length() > 10 ? c.getDni().substring(0, 10) : c.getDni();
        String email = c.getEmail().length() > 25 ? c.getEmail().substring(0, 25) : c.getEmail();
        String telefono = c.getTelefono().length() > 12 ? c.getTelefono().substring(0, 12) : c.getTelefono();

        System.out.printf("%-4d | %-18s | %-10s | %-25s | %-12s%n",
                c.getId(), nombreCompleto, dni, email, telefono);
    }
}