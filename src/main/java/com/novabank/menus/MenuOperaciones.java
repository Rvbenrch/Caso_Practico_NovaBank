package com.novabank.menus;

import com.novabank.model.Cuenta;
import com.novabank.service.CuentaService;

import java.util.Scanner;

public class MenuOperaciones {

    private Scanner scanner;
    private CuentaService cuentaService;

    public MenuOperaciones(Scanner scanner, CuentaService cuentaService) {
        this.scanner = scanner;
        this.cuentaService = cuentaService;
    }

    public void mostrar() {

        int opcion;

        do {
            System.out.println("\n--- OPERACIONES FINANCIERAS ---");
            System.out.println("1. Ingresar dinero");
            System.out.println("2. Retirar dinero");
            System.out.println("3. Transferir dinero");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    ingresar();
                    break;
                case 2:
                    retirar();
                    break;
                case 3:
                    transferir();
                    break;
                case 4:
                    System.out.println("Volviendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 4);
    }

    private void ingresar() {

        try {

            System.out.print("Número de cuenta: ");
            String numeroCuenta = scanner.nextLine();

            System.out.print("Cantidad a depositar: ");
            double cantidad = scanner.nextDouble();
            scanner.nextLine(); // limpiar buffer

            Cuenta cuenta = cuentaService.ingresar(numeroCuenta, cantidad);

            System.out.println("\nDepósito realizado correctamente.");
            System.out.println("Cuenta: " + cuenta.getNumeroCuenta());
            System.out.println("Importe: +" + String.format("%.2f", cantidad) + " €");
            System.out.println("Nuevo saldo: " + String.format("%.2f", cuenta.getSaldo()) + " €");

        } catch (Exception e) {

            System.out.println("\nERROR: " + e.getMessage());
        }
    }

    private void retirar() {

        try {

            System.out.print("Número de cuenta: ");
            String numeroCuenta = scanner.nextLine();

            System.out.print("Cantidad a retirar: ");
            double cantidad = scanner.nextDouble();
            scanner.nextLine(); // limpiar buffer

            cuentaService.retirar(numeroCuenta, cantidad);

            Cuenta cuentaActualizada = cuentaService.buscarPorNumeroCuenta(numeroCuenta);

            System.out.println("\nRetirada realizada correctamente.");
            System.out.println("Cuenta: " + cuentaActualizada.getNumeroCuenta());
            System.out.println("Importe: -" + String.format("%.2f", cantidad) + " €");
            System.out.println("Nuevo saldo: " + String.format("%.2f", cuentaActualizada.getSaldo()) + " €");

        } catch (Exception e) {

            System.out.println("\nERROR: " + e.getMessage());
        }
    }


    private void transferir() {

        try {

            System.out.print("Número de cuenta origen: ");
            String numeroOrigen = scanner.nextLine();

            System.out.print("Número de cuenta destino: ");
            String numeroDestino = scanner.nextLine();

            System.out.print("Cantidad a transferir: ");
            double cantidad = scanner.nextDouble();
            scanner.nextLine(); // limpiar buffer

            cuentaService.transferir(numeroOrigen, numeroDestino, cantidad);

            System.out.println("\nTransferencia realizada correctamente.");
            System.out.println("Cuenta origen:  " + numeroOrigen +
                    "  →  -" + String.format("%.2f", cantidad) + " €");
            System.out.println("Cuenta destino: " + numeroDestino +
                    "  →  +" + String.format("%.2f", cantidad) + " €");

        } catch (Exception e) {

            System.out.println("\nERROR: " + e.getMessage());
        }
    }
}







