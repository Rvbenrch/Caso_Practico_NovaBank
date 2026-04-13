package com.novabank.menus;

import com.novabank.exception.ClienteNoEncontradoException;
import com.novabank.model.Cliente;
import com.novabank.model.Cuenta;
import com.novabank.service.ClienteService;
import com.novabank.service.CuentaService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuCuentas {

    private Scanner scanner;
    private CuentaService cuentaService;
    private ClienteService clienteService;

    public MenuCuentas(Scanner scanner, CuentaService cuentaService, ClienteService clienteService) {
        this.scanner = scanner;
        this.cuentaService = cuentaService;
        this.clienteService = clienteService;
    }

    public void mostrar() {
        int opcion;

        do {
            System.out.println("\n--- GESTION DE CUENTAS ---");
            System.out.println("1. Crear cuenta");
            System.out.println("2. Listar cuentas de cliente");
            System.out.println("3. Ver informacion de cuenta");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opcion: ");

            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    crearCuenta();
                    break;
                case 2:
                    listarCuentasCliente();
                    break;
                case 3:
                    verCuenta();
                    break;
                case 4:
                    System.out.println("Volviendo...");
                    break;
                default:
                    System.out.println("Opción invalida.");
            }

        } while (opcion != 4);
    }

    private void crearCuenta() {
        try {
            System.out.print("Introduzca ID del cliente: ");
            long clienteId = Long.parseLong(scanner.nextLine());

            Cuenta cuenta = cuentaService.crearCuenta(clienteId);

            System.out.println("\nCuenta creada correctamente:");
            mostrarCuentaResumen(cuenta);

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void listarCuentasCliente() {
        try {
            System.out.print("Introduzca ID del cliente: ");
            long clienteId = Long.parseLong(scanner.nextLine());

            List<Cuenta> cuentas = cuentaService.listarCuentasPorCliente(clienteId);

            if (cuentas.isEmpty()) {
                System.out.println("El cliente no tiene cuentas.");
                return;
            }

            System.out.println("\nCuentas del cliente:");
            System.out.printf("%-28s | %-12s%n", "Número de cuenta", "Saldo");
            System.out.println("-----------------------------|--------------");

            for (Cuenta c : cuentas) {
                System.out.printf("%-28s | %-12s%n",
                        c.getNumeroCuenta(),
                        formatearSaldo(c.getSaldo()));
            }

        } catch (ClienteNoEncontradoException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void verCuenta() {
        try {
            System.out.print("Introduzca numero de cuenta: ");
            String numeroCuenta = scanner.nextLine();

            Cuenta cuenta = cuentaService.buscarPorNumeroCuenta(numeroCuenta);

            System.out.println("\n--- INFORMACION DE CUENTA ---");
            mostrarCuentaResumen(cuenta);

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void mostrarCuentaResumen(Cuenta cuenta) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Cliente titular = clienteService.encontrarPorId(cuenta.getTitularId());

        System.out.println("Numero de cuenta: " + cuenta.getNumeroCuenta());
        System.out.println("Titular: " + titular.getNombre() + " " + titular.getApellidos());
        System.out.println("Saldo: " + formatearSaldo(cuenta.getSaldo()));
        System.out.println("Fecha de creacion: " + cuenta.getFechaCreacion().format(formatter));
    }

    private String formatearSaldo(double saldo) {
        return String.format("%,.2f €", saldo);
    }
}