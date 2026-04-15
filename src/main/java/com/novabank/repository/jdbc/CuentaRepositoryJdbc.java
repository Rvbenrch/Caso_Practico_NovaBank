package com.novabank.repository.jdbc;

import com.novabank.config.DatabaseConnectionManager;
import com.novabank.model.Cuenta;
import com.novabank.repository.CuentaRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CuentaRepositoryJdbc implements CuentaRepository {

    @Override
    public Cuenta guardar(Cuenta cuenta) {
        String sql = "INSERT INTO cuentas (numero_cuenta, titular_id, saldo, fecha_creacion) " +
                "VALUES (?, ?, ?, ?) RETURNING id";

        try (Connection connection = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, cuenta.getNumeroCuenta());
            stmt.setLong(2, cuenta.getTitularId());
            stmt.setDouble(3, cuenta.getSaldo());
            stmt.setTimestamp(4, Timestamp.valueOf(cuenta.getFechaCreacion()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cuenta.setId(rs.getLong("id"));
            }

            return cuenta;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar cuenta", e);
        }
    }

    @Override
    public Optional<Cuenta> buscarPorId(Long id) {
        String sql = "SELECT * FROM cuentas WHERE id = ?";

        try (Connection connection = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cuenta por ID", e);
        }
    }

    @Override
    public Optional<Cuenta> buscarPorNumero(String numeroCuenta) {
        String sql = "SELECT * FROM cuentas WHERE numero_cuenta = ?";

        try (Connection connection = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, numeroCuenta);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cuenta por número", e);
        }
    }


    @Override
    public Optional<Cuenta> buscarPorNumero(String numeroCuenta, Connection conn) {
        String sql = "SELECT * FROM cuentas WHERE numero_cuenta = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroCuenta);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cuenta por número (transacción)", e);
        }
    }

    @Override
    public List<Cuenta> listarPorCliente(Long clienteId) {
        String sql = "SELECT * FROM cuentas WHERE titular_id = ?";

        try (Connection connection = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, clienteId);
            ResultSet rs = stmt.executeQuery();

            List<Cuenta> cuentas = new ArrayList<>();
            while (rs.next()) {
                cuentas.add(mapRow(rs));
            }

            return cuentas;

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar cuentas por cliente", e);
        }
    }

    @Override
    public void actualizarSaldo(Long cuentaId, double nuevoSaldo) {
        String sql = "UPDATE cuentas SET saldo = ? WHERE id = ?";

        try (Connection connection = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDouble(1, nuevoSaldo);
            stmt.setLong(2, cuentaId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar saldo", e);
        }
    }


    @Override
    public void actualizarSaldo(Long cuentaId, double nuevoSaldo, Connection conn) {
        String sql = "UPDATE cuentas SET saldo = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, nuevoSaldo);
            stmt.setLong(2, cuentaId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar saldo (transacción)", e);
        }
    }

    private Cuenta mapRow(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String numeroCuenta = rs.getString("numero_cuenta");
        Long titularId = rs.getLong("titular_id");
        double saldo = rs.getDouble("saldo");
        LocalDateTime fechaCreacion = rs.getTimestamp("fecha_creacion").toLocalDateTime();

        return new Cuenta(id, numeroCuenta, titularId, saldo, fechaCreacion);
    }
}