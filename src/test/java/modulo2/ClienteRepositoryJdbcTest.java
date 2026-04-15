package com.novabank.repository;

import com.novabank.config.DatabaseConnectionManager;
import com.novabank.model.Cliente;
import com.novabank.repository.jdbc.ClienteRepositoryJdbc;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClienteRepositoryJdbcTest {

    private ClienteRepositoryJdbc repository;

    @BeforeEach
    void setUp() throws Exception {
        repository = new ClienteRepositoryJdbc();

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("TRUNCATE TABLE clientes RESTART IDENTITY CASCADE");
        }
    }

    @Test
    @Order(1)
    void guardar_debeInsertarCliente() {
        Cliente cliente = new Cliente.Builder()
                .nombre("Ruben")
                .apellidos("Rodriguez")
                .dni("12345678A")
                .email("ruben@test.com")
                .telefono("600000001")
                .build();

        Cliente guardado = repository.guardar(cliente);

        assertNotNull(guardado.getId());
        assertEquals("Ruben", guardado.getNombre());
    }

    @Test
    @Order(2)
    void buscarPorId_debeRetornarCliente() {
        Cliente cliente = new Cliente.Builder()
                .nombre("Ana")
                .apellidos("Lopez")
                .dni("11111111B")
                .email("ana@test.com")
                .telefono("600000002")
                .build();

        Cliente guardado = repository.guardar(cliente);

        Optional<Cliente> encontrado = repository.buscarPorId(guardado.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("Ana", encontrado.get().getNombre());
    }

    @Test
    @Order(3)
    void buscarPorDni_debeRetornarCliente() {
        Cliente cliente = new Cliente.Builder()
                .nombre("Luis")
                .apellidos("Perez")
                .dni("22222222C")
                .email("luis@test.com")
                .telefono("600000003")
                .build();

        repository.guardar(cliente);

        Optional<Cliente> encontrado = repository.buscarPorDni("22222222C");

        assertTrue(encontrado.isPresent());
        assertEquals("Luis", encontrado.get().getNombre());
    }

    @Test
    @Order(4)
    void listar_debeRetornarListaCompleta() {
        repository.guardar(new Cliente.Builder()
                .nombre("A")
                .apellidos("A")
                .dni("A1")
                .email("a@test.com")
                .telefono("600000004")
                .build());

        repository.guardar(new Cliente.Builder()
                .nombre("B")
                .apellidos("B")
                .dni("B1")
                .email("b@test.com")
                .telefono("600000005")
                .build());

        List<Cliente> lista = repository.listar();

        assertEquals(2, lista.size());
    }
}