package modulo2;

import com.novabank.exception.ClienteDuplicadoException;
import com.novabank.exception.ClienteNoEncontradoException;
import com.novabank.model.Cliente;
import com.novabank.repository.ClienteRepository;
import com.novabank.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void crearCliente_valido_debeGuardar() {
        Cliente cliente = new Cliente.Builder()
                .nombre("Ruben")
                .apellidos("Rodriguez")
                .dni("12345678A")
                .email("ruben@email.com")
                .telefono("600000001")
                .build();

        when(repository.buscarPorDni("12345678A")).thenReturn(Optional.empty());
        when(repository.guardar(any())).thenReturn(cliente);

        Cliente creado = clienteService.crearCliente(
                "Ruben", "Rodriguez", "12345678A", "ruben@email.com", "600000001"
        );

        assertNotNull(creado);
        assertEquals("Ruben", creado.getNombre());
        verify(repository).guardar(any());
    }

    @Test
    void crearCliente_dniDuplicado_debeLanzarExcepcion() {
        when(repository.buscarPorDni("12345678A"))
                .thenReturn(Optional.of(mock(Cliente.class)));

        assertThrows(ClienteDuplicadoException.class, () ->
                clienteService.crearCliente("Ruben", "Rodriguez", "12345678A",
                        "ruben@email.com", "600000001")
        );
    }

    @Test
    void crearCliente_emailInvalido_debeLanzarExcepcion() {
        when(repository.buscarPorDni("98765432B")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                clienteService.crearCliente("Ana", "Lopez", "98765432B",
                        "analopez", "600000003")
        );
    }

    @Test
    void encontrarPorId_inexistente_debeLanzarExcepcion() {
        when(repository.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThrows(ClienteNoEncontradoException.class, () ->
                clienteService.encontrarPorId(99L)
        );
    }

    @Test
    void listarClientes_debeRetornarLista() {
        when(repository.listar()).thenReturn(List.of(mock(Cliente.class)));

        List<Cliente> lista = clienteService.listarClientes();

        assertEquals(1, lista.size());
        verify(repository).listar();
    }
}