package main.java.com.novabank;

import main.java.com.novabank.model.Cliente;
import main.java.com.novabank.repository.ClienteRepository;
import main.java.com.novabank.service.ClienteService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ClienteRepository clienteRepository = new ClienteRepository();
        ClienteService clienteService = new ClienteService(clienteRepository);

        Scanner scanner = new Scanner(System.in);
        int opcion;


        // MENU PRINCIPAL
        do {
            System.out.println("====================================");
            System.out.println("NOVABANK - SISTEMA DE OPERACIONES");
            System.out.println("====================================");
            System.out.println("1. Gestión de clientes");
            System.out.println("2. Gestión de cuentas");
            System.out.println("3. Operaciones financieras");
            System.out.println("4. Consultas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    menuClientes(scanner, clienteService);
                    break;
                case 2:
                    System.out.println("Funcionalidad aún no implementada.");
                    break;
                case 3:
                    System.out.println("Funcionalidad aún no implementada.");
                    break;
                case 4:
                    System.out.println("Funcionalidad aún no implementada.");
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 5);

        scanner.close();
    }




    // OPCIÓN 1 - MENU DE CLIENTES
    private static void menuClientes(Scanner scanner, ClienteService clienteService) {

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

            switch (opcion) {

                case 1:
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
                        System.out.println("Cliente creado correctamente.");
                        System.out.println("ID cliente: " + cliente.getId() + " y su nombre completo es: ");
                        System.out.println(cliente.getNombre()+ " " + cliente.getApellidos());
                        System.out.println("Cliente creado correctamente.");

                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                    break;

                case 2:

                    System.out.println("1. Buscar por ID");
                    System.out.println("2. Buscar por DNI");
                    System.out.print("Seleccione una opción: ");

                    int opcionBusqueda = scanner.nextInt();
                    scanner.nextLine();

                    if (opcionBusqueda == 1) {
                        System.out.print("Introduzca el ID: ");
                        long id = scanner.nextLong();
                        scanner.nextLine();
                        System.out.println("El cliente al que le corresponde el ID: " + clienteService.encontrarPorId(id).getNombre() + " " + clienteService.encontrarPorId(id).getApellidos());



                    } else if (opcionBusqueda == 2) {
                        System.out.print("Introduzca el DNI: ");
                        String dni = scanner.nextLine();

                        System.out.println(clienteService.encontrarPorDni(dni).getNombre() + " " +  clienteService.encontrarPorDni(dni).getApellidos() + " tiene el dni: " + clienteService.encontrarPorDni(dni).getDni());

                    } else {
                        System.out.println("Opción inválida.");
                    }

                    break;

                case 3:
                    System.out.println("Listar clientes - pendiente de implementar");
                    break;
                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 4);
    }

}

