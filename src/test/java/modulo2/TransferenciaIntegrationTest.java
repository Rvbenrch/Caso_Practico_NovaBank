package modulo2;

import com.novabank.config.DatabaseConnectionManager;
import com.novabank.model.Cuenta;
import com.novabank.model.Movimiento;
import com.novabank.model.TipoMovimiento;
import com.novabank.repository.jdbc.CuentaRepositoryJdbc;
import com.novabank.repository.jdbc.MovimientoRepositoryJdbc;
import com.novabank.service.ClienteService;
import com.novabank.service.impl.CuentaServiceImpl;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransferenciaIntegrationTest {

    private CuentaRepositoryJdbc cuentaRepo;
    private MovimientoRepositoryJdbc movRepo;
    private ClienteService clienteServiceMock;
    private CuentaServiceImpl cuentaService;

    @BeforeEach
    void setUp() throws Exception {
        cuentaRepo = new CuentaRepositoryJdbc();
        movRepo = new MovimientoRepositoryJdbc();
        clienteServiceMock = mock(ClienteService.class);

        cuentaService = new CuentaServiceImpl(cuentaRepo, clienteServiceMock, movRepo);

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
    void transferenciaCorrecta_debeMoverDineroYCrearMovimientos() {
        Cuenta origen = new Cuenta(null, "ORIGEN123", 1L, 1000.0, LocalDateTime.now());
        Cuenta destino = new Cuenta(null, "DESTINO123", 1L, 500.0, LocalDateTime.now());

        cuentaRepo.guardar(origen);
        cuentaRepo.guardar(destino);

        cuentaService.transferir("ORIGEN123", "DESTINO123", 300);

        Cuenta origenFinal = cuentaRepo.buscarPorNumero("ORIGEN123").orElseThrow();
        Cuenta destinoFinal = cuentaRepo.buscarPorNumero("DESTINO123").orElseThrow();

        assertEquals(700.0, origenFinal.getSaldo());
        assertEquals(800.0, destinoFinal.getSaldo());

        List<Movimiento> movsOrigen = movRepo.listarPorCuenta(origenFinal.getId());
        List<Movimiento> movsDestino = movRepo.listarPorCuenta(destinoFinal.getId());

        assertEquals(1, movsOrigen.size());
        assertEquals(1, movsDestino.size());

        assertEquals(TipoMovimiento.TRANSFERENCIA_SALIENTE, movsOrigen.get(0).getTipo());
        assertEquals(TipoMovimiento.TRANSFERENCIA_ENTRANTE, movsDestino.get(0).getTipo());
    }

    @Test
    @Order(2)
    void transferenciaFallida_debeHacerRollback() {
        Cuenta origen = new Cuenta(null, "ORIGENFAIL", 1L, 1000.0, LocalDateTime.now());
        cuentaRepo.guardar(origen);

        assertThrows(RuntimeException.class, () ->
                cuentaService.transferir("ORIGENFAIL", "NO_EXISTE", 200)
        );

        Cuenta origenFinal = cuentaRepo.buscarPorNumero("ORIGENFAIL").orElseThrow();

        assertEquals(1000.0, origenFinal.getSaldo());

        List<Movimiento> movs = movRepo.listarPorCuenta(origenFinal.getId());
        assertEquals(0, movs.size());
    }
}