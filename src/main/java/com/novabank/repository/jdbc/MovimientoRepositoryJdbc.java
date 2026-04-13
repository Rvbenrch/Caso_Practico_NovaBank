package com.novabank.repository.jdbc;

import com.novabank.config.DatabaseConnectionManager;
import com.novabank.model.Movimiento;
import com.novabank.model.TipoMovimiento;
import com.novabank.repository.MovimientoRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovimientoRepositoryJdbc implements MovimientoRepository {

    @Override
    public Movimiento guardar(Movimiento movimiento) {
        String sql = "INSERT INTO movimientos (cuenta_id, tipo, importe, fecha) VALUES (?, ?, ?, ?) RETURNING id";

        try (Connection connection = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, movimiento.getCuentaId());
            stmt.setString(2, movimiento.getTipo().name());
            stmt.setDouble(3, movimiento.getImporte());
            stmt.setTimestamp(4, Timestamp.valueOf(movimiento.getFecha()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                movimiento.setId(rs.getLong("id"));
            }

            return movimiento;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar movimiento", e);
        }
    }

    @Override
    public List<Movimiento> listarPorCuenta(Long cuentaId) {
        String sql = "SELECT * FROM movimientos WHERE cuenta_id = ? ORDER BY fecha DESC";

        try (Connection connection = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, cuentaId);
            ResultSet rs = stmt.executeQuery();

            List<Movimiento> movimientos = new ArrayList<>();
            while (rs.next()) {
                movimientos.add(mapRow(rs));
            }

            return movimientos;

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar movimientos por cuenta", e);
        }
    }

    @Override
    public List<Movimiento> listarPorCuentaYRango(Long cuentaId, LocalDate inicio, LocalDate fin) {
        String sql = "SELECT * FROM movimientos WHERE cuenta_id = ? AND fecha BETWEEN ? AND ? ORDER BY fecha DESC";

        try (Connection connection = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, cuentaId);
            stmt.setTimestamp(2, Timestamp.valueOf(inicio.atStartOfDay()));
            stmt.setTimestamp(3, Timestamp.valueOf(fin.plusDays(1).atStartOfDay()));

            ResultSet rs = stmt.executeQuery();

            List<Movimiento> movimientos = new ArrayList<>();
            while (rs.next()) {
                movimientos.add(mapRow(rs));
            }

            return movimientos;

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar movimientos por rango", e);
        }
    }

    private Movimiento mapRow(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        Long cuentaId = rs.getLong("cuenta_id");
        TipoMovimiento tipo = TipoMovimiento.valueOf(rs.getString("tipo"));
        double importe = rs.getDouble("importe");
        LocalDateTime fecha = rs.getTimestamp("fecha").toLocalDateTime();

        return new Movimiento(id, cuentaId, tipo, importe, fecha);
    }
}