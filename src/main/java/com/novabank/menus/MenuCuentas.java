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
            System.out.println("\n--- GESTIÓN DE CUENTAS ---");
            System.out.println("1. Crear cuenta");
            System.out.println("2. Listar cuentas de cliente");
            System.out.println("3. Ver información de cuenta");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opción: ");

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
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 4);
    }

    private void crearCuenta() {
        System.out.print("Introduzca ID del cliente: ");
        long clienteId = Long.parseLong(scanner.nextLine());

        try {
            Cuenta cuenta = cuentaService.crearCuenta(clienteId);
            System.out.println("\nCuenta creada correctamente:");
            mostrarCuentaResumen(cuenta);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void listarCuentasCliente() {
        System.out.print("Introduzca ID del cliente: ");
        long clienteId = Long.parseLong(scanner.nextLine());

        try {
            List<Cuenta> cuentas = cuentaService.listarCuentasPorCliente(clienteId);
            if (cuentas.isEmpty()) {
                System.out.println("El cliente no tiene cuentas.");
                return;
            }

            System.out.println("\nCuentas del cliente:");
            System.out.printf("%-28s | %-10s%n", "Número de cuenta", "Saldo");
            System.out.println("-----------------------------|------------");
            for (Cuenta c : cuentas) {
                System.out.printf("%-28s | %s%n", c.getNumeroCuenta(), formatearSaldo(c.getSaldo()));
            }

        } catch (ClienteNoEncontradoException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void verCuenta() {
        System.out.print("Introduzca número de cuenta: ");
        String numeroCuenta = scanner.nextLine();

        try {
            Cuenta cuenta = cuentaService.buscarPorNumeroCuenta(numeroCuenta);

            System.out.println("Información de la cuenta:");
            mostrarCuentaResumen(cuenta);

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void mostrarCuentaResumen(Cuenta cuenta) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Número de cuenta: " + cuenta.getNumeroCuenta());
        System.out.println("Titular: " + cuenta.getTitular().getNombre() + " " + cuenta.getTitular().getApellidos());
        System.out.println("Saldo: " + formatearSaldo(cuenta.getSaldo()));
        System.out.println("Fecha de creación: " + cuenta.getFechaCreacion().format(formatter));
    }

    private String formatearSaldo(double saldo) {
        return String.format("%,.2f €", saldo);
    }
}