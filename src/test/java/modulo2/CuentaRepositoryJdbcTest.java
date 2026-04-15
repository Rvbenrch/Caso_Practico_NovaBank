package com.novabank.repository;

import com.novabank.config.DatabaseConnectionManager;
import com.novabank.model.Cuenta;
import com.novabank.repository.jdbc.CuentaRepositoryJdbc;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CuentaRepositoryJdbcTest {

    private CuentaRepositoryJdbc repository;

    @BeforeEach
    void setUp() throws Exception {
        repository = new CuentaRepositoryJdbc();

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("TRUNCATE TABLE movimientos RESTART IDENTITY CASCADE");
            stmt.execute("TRUNCATE TABLE cuentas RESTART IDENTITY CASCADE");
            stmt.execute("TRUNCATE TABLE clientes RESTART IDENTITY CASCADE");

            stmt.execute("INSERT INTO clientes (nombre, apellidos, dni, email, telefono) VALUES " +
                    "('Ruben', 'Rodriguez', '12345678A', 'ruben@test.com', '600000001')");
        }
    }

    @Test
    @Order(1)
    void guardar_debeInsertarCuenta() {
        Cuenta cuenta = new Cuenta(
                null,
                "ES91TEST1",
                1L,
                100.0,
                LocalDateTime.now()
        );

        Cuenta guardada = repository.guardar(cuenta);

        assertNotNull(guardada.getId());
        assertEquals("ES91TEST1", guardada.getNumeroCuenta());
    }

    @Test
    @Order(2)
    void buscarPorId_debeRetornarCuenta() {
        Cuenta cuenta = new Cuenta(
                null,
                "ES91TEST2",
                1L,
                200.0,
                LocalDateTime.now()
        );

        Cuenta guardada = repository.guardar(cuenta);

        Optional<Cuenta> encontrada = repository.buscarPorId(guardada.getId());

        assertTrue(encontrada.isPresent());
        assertEquals("ES91TEST2", encontrada.get().getNumeroCuenta());
    }

    @Test
    @Order(3)
    void buscarPorNumero_debeRetornarCuenta() {
        Cuenta cuenta = new Cuenta(
                null,
                "ES91TEST3",
                1L,
                300.0,
                LocalDateTime.now()
        );

        repository.guardar(cuenta);

        Optional<Cuenta> encontrada = repository.buscarPorNumero("ES91TEST3");

        assertTrue(encontrada.isPresent());
        assertEquals(300.0, encontrada.get().getSaldo());
    }

    @Test
    @Order(4)
    void listarPorCliente_debeRetornarCuentas() {
        repository.guardar(new Cuenta(null, "ES91A", 1L, 100.0, LocalDateTime.now()));
        repository.guardar(new Cuenta(null, "ES91B", 1L, 200.0, LocalDateTime.now()));

        List<Cuenta> cuentas = repository.listarPorCliente(1L);

        assertEquals(2, cuentas.size());
    }

    @Test
    @Order(5)
    void actualizarSaldo_debeModificarSaldo() {
        Cuenta cuenta = new Cuenta(
                null,
                "ES91TEST4",
                1L,
                500.0,
                LocalDateTime.now()
        );

        Cuenta guardada = repository.guardar(cuenta);

        repository.actualizarSaldo(guardada.getId(), 999.0);

        Optional<Cuenta> actualizada = repository.buscarPorId(guardada.getId());

        assertTrue(actualizada.isPresent());
        assertEquals(999.0, actualizada.get().getSaldo());
    }

    @Test
    @Order(6)
    void buscarPorNumero_conexionTransaccional_debeFuncionar() throws Exception {
        Cuenta cuenta = new Cuenta(
                null,
                "ES91TRANS",
                1L,
                700.0,
                LocalDateTime.now()
        );

        repository.guardar(cuenta);

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection()) {
            Optional<Cuenta> encontrada = repository.buscarPorNumero("ES91TRANS", conn);

            assertTrue(encontrada.isPresent());
            assertEquals(700.0, encontrada.get().getSaldo());
        }
    }
}