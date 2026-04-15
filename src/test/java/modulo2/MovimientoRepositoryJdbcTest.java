package com.novabank.repository;

import com.novabank.config.DatabaseConnectionManager;
import com.novabank.model.Movimiento;
import com.novabank.model.TipoMovimiento;
import com.novabank.repository.jdbc.MovimientoRepositoryJdbc;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovimientoRepositoryJdbcTest {

    private MovimientoRepositoryJdbc repository;

    @BeforeEach
    void setUp() throws Exception {
        repository = new MovimientoRepositoryJdbc();

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("TRUNCATE TABLE movimientos RESTART IDENTITY CASCADE");
            stmt.execute("TRUNCATE TABLE cuentas RESTART IDENTITY CASCADE");
            stmt.execute("TRUNCATE TABLE clientes RESTART IDENTITY CASCADE");

            stmt.execute("INSERT INTO clientes (nombre, apellidos, dni, email, telefono) VALUES " +
                    "('Ruben', 'Rodriguez', '12345678A', 'ruben@test.com', '600000001')");

            stmt.execute("INSERT INTO cuentas (numero_cuenta, titular_id, saldo, fecha_creacion) VALUES " +
                    "('ES91TEST', 1, 1000, NOW())");
        }
    }

    @Test
    @Order(1)
    void guardar_debeInsertarMovimiento() {
        Movimiento mov = new Movimiento(
                null,
                1L,
                TipoMovimiento.DEPOSITO,
                500.0,
                LocalDateTime.now()
        );

        Movimiento guardado = repository.guardar(mov);

        assertNotNull(guardado.getId());
        assertEquals(1L, guardado.getCuentaId());
        assertEquals(TipoMovimiento.DEPOSITO, guardado.getTipo());
    }

    @Test
    @Order(2)
    void listarPorCuenta_debeRetornarMovimientos() {
        repository.guardar(new Movimiento(null, 1L, TipoMovimiento.DEPOSITO, 100, LocalDateTime.now()));
        repository.guardar(new Movimiento(null, 1L, TipoMovimiento.RETIRO, 50, LocalDateTime.now()));

        List<Movimiento> lista = repository.listarPorCuenta(1L);

        assertEquals(2, lista.size());
        assertTrue(lista.stream().anyMatch(m -> m.getTipo() == TipoMovimiento.DEPOSITO));
        assertTrue(lista.stream().anyMatch(m -> m.getTipo() == TipoMovimiento.RETIRO));
    }

    @Test
    @Order(3)
    void listarPorCuentaYRango_debeRetornarMovimientosEnRango() {
        LocalDateTime ahora = LocalDateTime.now();

        repository.guardar(new Movimiento(null, 1L, TipoMovimiento.DEPOSITO, 100, ahora.minusDays(5)));
        repository.guardar(new Movimiento(null, 1L, TipoMovimiento.DEPOSITO, 200, ahora.minusDays(2)));
        repository.guardar(new Movimiento(null, 1L, TipoMovimiento.RETIRO, 50, ahora.minusDays(1)));

        List<Movimiento> lista = repository.listarPorCuentaYRango(
                1L,
                LocalDate.now().minusDays(3),
                LocalDate.now()
        );

        assertEquals(2, lista.size());
        assertTrue(lista.stream().allMatch(m -> m.getImporte() == 200 || m.getImporte() == 50));
    }
}