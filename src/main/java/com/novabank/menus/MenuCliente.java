package com.novabank.menus;

import com.novabank.model.Cliente;
import com.novabank.service.ClienteService;
import com.novabank.service.CuentaService;

import java.util.List;
import java.util.Scanner;

public class MenuCliente {

    public static void menuClientes(Scanner scanner,
                                    ClienteService clienteService,
                                    CuentaService cuentaService) {

        int opcion;

        do {
            System.out.println("\n--- GESTIÓN DE CLIENTES ---");
            System.out.println("1. Crear cliente");
            System.out.println("2. Buscar cliente");
            System.out.println("3. Listar clientes");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer
            System.out.println("-------------------------");

            switch (opcion) {
                case 1:
                    crearCliente(scanner, clienteService);
                    break;

                case 2:
                    buscarCliente(scanner, clienteService);
                    break;

                case 3:
                    listarClientes(clienteService);
                    break;

                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 4);
    }

    private static void crearCliente(Scanner scanner, ClienteService clienteService) {
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

            System.out.println("Cliente creado correctamente:");
            mostrarCliente(cliente);

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void buscarCliente(Scanner scanner, ClienteService clienteService) {
        System.out.println("1. Buscar por ID");
        System.out.println("2. Buscar por DNI");
        System.out.print("Seleccione una opción: ");

        int opcionBusqueda = scanner.nextInt();
        scanner.nextLine();

        try {
            Cliente cliente = null;
            if (opcionBusqueda == 1) {
                System.out.print("Introduzca el ID: ");
                long id = scanner.nextLong();
                scanner.nextLine();
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
                mostrarCliente(cliente);
            } else {
                System.out.println("Cliente no encontrado.");
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void listarClientes(ClienteService clienteService) {
        List<Cliente> clientes = clienteService.listarClientes();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        System.out.printf("%-4s | %-18s | %-10s | %-25s | %-12s%n",
                "ID", "Nombre", "DNI", "Email", "Teléfono");
        System.out.println("-----|--------------------|------------|---------------------------|-------------");

        for (Cliente c : clientes) {
            mostrarCliente(c);
        }
    }

    private static void mostrarCliente(Cliente c) {
        String nombreCompleto = c.getNombre() + " " + c.getApellidos();
        nombreCompleto = nombreCompleto.length() > 18 ? nombreCompleto.substring(0, 18) : nombreCompleto;
        String dni = c.getDni().length() > 10 ? c.getDni().substring(0, 10) : c.getDni();
        String email = c.getEmail().length() > 25 ? c.getEmail().substring(0, 25) : c.getEmail();
        String telefono = c.getTelefono().length() > 12 ? c.getTelefono().substring(0, 12) : c.getTelefono();

        System.out.printf("%-4d | %-18s | %-10s | %-25s | %-12s%n",
                c.getId(),
                nombreCompleto,
                dni,
                email,
                telefono);
    }
}