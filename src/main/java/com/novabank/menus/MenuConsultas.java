package com.novabank.menus;

import com.novabank.model.Movimiento;
import com.novabank.model.TipoMovimiento;
import com.novabank.service.ConsultaService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuConsultas {

    private Scanner scanner;
    private ConsultaService consultaService;

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter FORMATO_FECHA_HORA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public MenuConsultas(Scanner scanner, ConsultaService consultaService) {
        this.scanner = scanner;
        this.consultaService = consultaService;
    }

    public void mostrar() {
        int opcion;

        do {
            System.out.println("\n--- CONSULTAS ---");
            System.out.println("1. Consultar saldo");
            System.out.println("2. Historial de movimientos");
            System.out.println("3. Movimientos por rango de fechas");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opcion: ");

            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    consultarSaldo();
                    break;
                case 2:
                    historialMovimientos();
                    break;
                case 3:
                    movimientosPorRango();
                    break;
                case 4:
                    System.out.println("Volviendo...");
                    break;
                default:
                    System.out.println("Opción invalida.");
            }

        } while (opcion != 4);
    }


    private void consultarSaldo() {
        try {
            System.out.print("Introduzca numero de cuenta: ");
            String numeroCuenta = scanner.nextLine();

            double saldo = consultaService.consultarSaldo(numeroCuenta);

            System.out.println("\nSaldo actual: " + formatearImporte(saldo));

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }


    private void historialMovimientos() {
        try {
            System.out.print("Introduzca numero de cuenta: ");
            String numeroCuenta = scanner.nextLine();

            List<Movimiento> movimientos =
                    consultaService.obtenerHistorial(numeroCuenta);

            System.out.println("\nHistorial de movimientos - " + numeroCuenta + ":\n");

            imprimirTabla(movimientos);

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void movimientosPorRango() {
        try {
            System.out.print("Numero de cuenta: ");
            String numeroCuenta = scanner.nextLine();

            System.out.print("Fecha inicio (yyyy-MM-dd): ");
            LocalDate inicio = LocalDate.parse(scanner.nextLine(), FORMATO_FECHA);

            System.out.print("Fecha fin   (yyyy-MM-dd): ");
            LocalDate fin = LocalDate.parse(scanner.nextLine(), FORMATO_FECHA);

            List<Movimiento> movimientos =
                    consultaService.obtenerMovimientosPorRango(numeroCuenta, inicio, fin);

            System.out.println("\nMovimientos del " + inicio + " al " + fin + ":\n");

            imprimirTabla(movimientos);

        } catch (DateTimeParseException e) {
            System.out.println("ERROR: Formato de fecha incorrecto.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void imprimirTabla(List<Movimiento> movimientos) {

        if (movimientos.isEmpty()) {
            System.out.println("No hay movimientos para mostrar.");
            return;
        }

        System.out.printf("%-20s | %-22s | %-12s%n",
                "Fecha", "Tipo", "Cantidad");
        System.out.println("---------------------|------------------------|------------");

        for (Movimiento m : movimientos) {

            String fecha = m.getFecha().format(FORMATO_FECHA_HORA);
            TipoMovimiento tipo = m.getTipo();

            String signo = (tipo == TipoMovimiento.RETIRO
                    || tipo == TipoMovimiento.TRANSFERENCIA_SALIENTE)
                    ? "-" : "+";

            String cantidad = signo + formatearImporte(m.getImporte());

            System.out.printf("%-20s | %-22s | %-12s%n",
                    fecha,
                    tipo,
                    cantidad);
        }
    }

    private String formatearImporte(double importe) {
        return String.format("%,.2f €", importe);
    }
}