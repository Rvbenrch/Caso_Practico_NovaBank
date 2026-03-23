package main.java.com.novabank.repository;

import main.java.com.novabank.model.Cuenta;
import java.util.ArrayList;
import java.util.List;

public class CuentaRepository {

    private List<Cuenta> cuentas = new ArrayList<>();
    public void guardar(Cuenta cuenta) {
        cuentas.add(cuenta);
    }
    public Cuenta buscarPorNumeroCuenta(String numeroCuenta) {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                return cuenta;
            }
        }
        return null;
    }
    public List<Cuenta> listarCuentas() {
        return new ArrayList<>(cuentas);

    }
    public List<Cuenta> buscarPorClienteId(Long clienteId) {
        List<Cuenta> resultado = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            if (cuenta.getTitular().getId().equals(clienteId)) {
                resultado.add(cuenta);
            }
        }

        return resultado;
    }



}
