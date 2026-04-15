package com.novabank.service.decorator;

import com.novabank.model.Cuenta;
import com.novabank.service.CuentaServiceInterface;

public class CuentaServiceVisualDecorator extends CuentaServiceDecorator {

    public CuentaServiceVisualDecorator(CuentaServiceInterface wrapped) {
        super(wrapped);
    }

    @Override
    public Cuenta ingresar(String numeroCuenta, double cantidad) {
        mostrarBanner("INGRESO");
        animarOperacion("Procesando ingreso");
        Cuenta cuenta = super.ingresar(numeroCuenta, cantidad);
        mostrarResultado("Ingreso completado correctamente.");
        return cuenta;
    }

    @Override
    public Cuenta retirar(String numeroCuenta, double cantidad) {
        mostrarBanner("RETIRADA");
        animarOperacion("Procesando retirada");
        Cuenta cuenta = super.retirar(numeroCuenta, cantidad);
        mostrarResultado("Retirada completada correctamente.");
        return cuenta;
    }

    @Override
    public void transferir(String origen, String destino, double cantidad) {
        mostrarBanner("TRANSFERENCIA");
        animarOperacion("Procesando transferencia");
        super.transferir(origen, destino, cantidad);
        mostrarResultado("Transferencia completada correctamente.");
    }


    private void mostrarBanner(String titulo) {
        System.out.println();
        System.out.println("──────────────────────────────────────────────");
        System.out.println("          NOVABANK · " + titulo);
        System.out.println("──────────────────────────────────────────────");
        System.out.println();
    }

    private void mostrarResultado(String mensaje) {

        System.out.println("");
    }

    private void animarOperacion(String mensaje) {
        System.out.println(mensaje + "...");
        animarCocheProgreso(30, 15, 60); // anchoBarra, pasos, delayMs
        System.out.println();
    }

    // ─────────────────────────────────────────────
    //  ANIMACIÓN DEL COCHE SOBRE LA BARRA
    // ─────────────────────────────────────────────

    private void animarCocheProgreso(int anchoBarra, int pasos, int delayMs) {
        // El coche empieza a la izquierda y avanza hacia la derecha
        for (int i = 0; i <= pasos; i++) {
            double progreso = (double) i / pasos;
            int posicionCoche = (int) (progreso * anchoBarra);

            StringBuilder barra = new StringBuilder();
            barra.append("|");

            for (int j = 0; j < anchoBarra; j++) {
                if (j == posicionCoche) {
                    barra.append("🚗");
                } else {
                    barra.append("-");
                }
            }

            barra.append("| ");
            int porcentaje = (int) (progreso * 100);
            barra.append(porcentaje).append("%");

            System.out.print("\r" + barra);
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}