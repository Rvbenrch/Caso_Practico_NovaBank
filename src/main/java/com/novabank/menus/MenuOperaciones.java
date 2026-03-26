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
            System.out.print("Seleccione una opcion: ");

            opcion = Integer.parseInt(scanner.nextLine());

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
            System.out.print("Numero de cuenta: ");
            String numeroCuenta = scanner.nextLine();

            System.out.print("Cantidad a depositar: ");
            double cantidad = Double.parseDouble(scanner.nextLine());

            Cuenta cuenta = cuentaService.ingresar(numeroCuenta, cantidad);

            System.out.println("\nDeposito realizado correctamente:");
            System.out.println("Cuenta: " + cuenta.getNumeroCuenta());
            System.out.println("Importe: +" + formatear(cantidad));
            System.out.println("Nuevo saldo: " + formatear(cuenta.getSaldo()));

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void retirar() {
        try {
            System.out.print("Numero de cuenta: ");
            String numeroCuenta = scanner.nextLine();

            System.out.print("Cantidad a retirar: ");
            double cantidad = Double.parseDouble(scanner.nextLine());

            Cuenta cuenta = cuentaService.retirar(numeroCuenta, cantidad);

            System.out.println("\nRetirada realizada correctamente:");
            System.out.println("Cuenta: " + cuenta.getNumeroCuenta());
            System.out.println("Importe: -" + formatear(cantidad));
            System.out.println("Nuevo saldo: " + formatear(cuenta.getSaldo()));

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void transferir() {
        try {
            System.out.print("Numero de cuenta origen: ");
            String origen = scanner.nextLine();

            System.out.print("Numero de cuenta destino: ");
            String destino = scanner.nextLine();

            System.out.print("Cantidad a transferir: ");
            double cantidad = Double.parseDouble(scanner.nextLine());

            cuentaService.transferir(origen, destino, cantidad);

            Cuenta cOrigen = cuentaService.buscarPorNumeroCuenta(origen);
            Cuenta cDestino = cuentaService.buscarPorNumeroCuenta(destino);

            System.out.println("\nTransferencia realizada correctamente:");
            System.out.println("Origen: -" + formatear(cantidad) + " → saldo: " + formatear(cOrigen.getSaldo()));
            System.out.println("Destino: +" + formatear(cantidad) + " → saldo: " + formatear(cDestino.getSaldo()));

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private String formatear(double cantidad) {
        return String.format("%,.2f €", cantidad);
    }
}