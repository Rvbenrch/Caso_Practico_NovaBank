package main.java.com.novabank;

import main.java.com.novabank.menus.MenuCuentas;
import main.java.com.novabank.menus.MenuOperaciones;
import main.java.com.novabank.repository.ClienteRepository;
import main.java.com.novabank.repository.CuentaRepository;
import main.java.com.novabank.service.ClienteService;
import main.java.com.novabank.service.CuentaService;

import java.util.Scanner;

import static main.java.com.novabank.menus.MenuCliente.menuClientes;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ClienteRepository clienteRepository = new ClienteRepository();
        CuentaRepository cuentaRepository = new CuentaRepository();

        ClienteService clienteService = new ClienteService(clienteRepository);
        CuentaService cuentaService = new CuentaService(cuentaRepository, clienteService);

        MenuOperaciones menuOperaciones =
                new MenuOperaciones(scanner, cuentaService);

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
                    menuClientes(scanner, clienteService, cuentaService);
                    break;

                case 2:
                    MenuCuentas menuCuentas =
                            new MenuCuentas(scanner, cuentaService, clienteService);
                    menuCuentas.mostrar();
                    break;

                case 3:
                    menuOperaciones.mostrar();
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
}
