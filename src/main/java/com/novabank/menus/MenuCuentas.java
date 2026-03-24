package main.java.com.novabank.menus;

import main.java.com.novabank.model.Cliente;
import main.java.com.novabank.model.Cuenta;
import main.java.com.novabank.service.ClienteService;
import main.java.com.novabank.service.CuentaService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuCuentas {

    private Scanner scanner;
    private CuentaService cuentaService;
    private ClienteService clienteService;


    public MenuCuentas(Scanner scanner,
                       CuentaService cuentaService,
                       ClienteService clienteService) {

        this.scanner = scanner;
        this.cuentaService = cuentaService;
        this.clienteService = clienteService;
    }


    public void mostrar() {

        int opcion;

        do {
            System.out.println("\n--- GESTIÓN DE CUENTAS ---");
            System.out.println("1. Crear cuenta");
            System.out.println("2. Listar cuentas de cliente");
            System.out.println("3. Ver información de cuenta");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {

                case 1:

                    System.out.print("Introduzca ID del cliente: ");
                    long clienteId1 = Long.parseLong(scanner.nextLine());

                    try {
                        Cuenta cuenta = cuentaService.crearCuenta(clienteId1);

                        System.out.println("\nCuenta creada correctamente.");
                        System.out.println("Número de cuenta: " + cuenta.getNumeroCuenta());
                        System.out.println("Titular: " +
                                cuenta.getTitular().getNombre() +
                                " (ID: " + cuenta.getTitular().getId() + ")");
                        System.out.println("Saldo inicial: 0,00 €");

                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }

                    break;


                case 2:

                    System.out.print("Introduzca ID del cliente: ");
                    long clienteId = Long.parseLong(scanner.nextLine());

                    try {

                        Cliente cliente = clienteService.encontrarPorId(clienteId);
                        List<Cuenta> cuentas = cuentaService.listarCuentasPorCliente(clienteId);

                        if (cuentas.isEmpty()) {
                            System.out.println("El cliente no tiene cuentas.");
                        } else {

                            System.out.println("\nCuentas del cliente " +
                                    cliente.getNombre() + " " + cliente.getApellidos() + ":");

                            System.out.printf("%-28s | %-10s%n", "Número de cuenta", "Saldo");
                            System.out.println("-----------------------------|------------");

                            for (Cuenta c : cuentas) {
                                System.out.printf("%-28s | %,.2f €%n",
                                        c.getNumeroCuenta(),
                                        c.getSaldo());
                            }
                        }

                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }

                    break;


                case 3:

                    System.out.print("Introduzca número de cuenta: ");
                    String numeroCuenta = scanner.nextLine();

                    try {

                        Cuenta cuenta = cuentaService.buscarPorNumeroCuenta(numeroCuenta);

                        System.out.println();
                        System.out.println("Número de cuenta: " + cuenta.getNumeroCuenta());
                        System.out.println("Titular:          " +
                                cuenta.getTitular().getNombre() + " " +
                                cuenta.getTitular().getApellidos());
                        System.out.printf("Saldo:            %,.2f €%n",
                                cuenta.getSaldo());
                        DateTimeFormatter formatter =
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                        System.out.println("Fecha de creación: " +
                                cuenta.getFechaCreacion().format(formatter));


                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }

                    break;


                case 4:
                    System.out.println("Volviendo...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 4);
    }
}
