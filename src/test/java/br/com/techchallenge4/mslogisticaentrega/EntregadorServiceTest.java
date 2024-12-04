package br.com.techchallenge4.mslogisticaentrega;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.techchallenge4.mslogisticaentrega.enums.StatusEntregadorEnum;
import br.com.techchallenge4.mslogisticaentrega.model.Entregador;
import br.com.techchallenge4.mslogisticaentrega.repository.EntregadorRepository;
import br.com.techchallenge4.mslogisticaentrega.service.EntregadorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class EntregadorServiceTest {

    @Autowired
    private EntregadorService entregadorService;

    @MockBean
    private EntregadorRepository entregadorRepository;

    @Mock
    private Entregador entregador;

    @BeforeEach
    public void setUp() {
        // Inicializa o mock do entregador antes de cada teste
        entregador = new Entregador(1L, "João", "Moto", StatusEntregadorEnum.DISPONIVEL);
    }

    @Test
    public void testCriarEntregador() {
        // Cenário: Criar um novo entregador

        when(entregadorRepository.save(any(Entregador.class))).thenReturn(entregador); // Simula a criação do entregador

        Entregador entregadorCriado = entregadorService.criarEntregador(entregador);

        assertNotNull(entregadorCriado);
        assertEquals(StatusEntregadorEnum.DISPONIVEL, entregadorCriado.getStatusEntregador()); // Verifica o status inicial
        verify(entregadorRepository, times(1)).save(entregador);  // Verifica se o método save foi chamado
    }

    @Test
    public void testListarEntregadores() {
        // Cenário: Listar todos os entregadores

        List<Entregador> entregadores = Arrays.asList(
                new Entregador(1L, "João", "Moto", StatusEntregadorEnum.DISPONIVEL),
                new Entregador(2L, "Maria", "Bicicleta", StatusEntregadorEnum.DISPONIVEL)
        );
        when(entregadorRepository.findAll()).thenReturn(entregadores); // Simula o retorno da lista de entregadores

        List<Entregador> entregadoresListados = entregadorService.listarEntregadores();

        assertNotNull(entregadoresListados);
        assertEquals(2, entregadoresListados.size()); // Verifica se a lista contém dois entregadores
        verify(entregadorRepository, times(1)).findAll();  // Verifica se o método findAll foi chamado
    }

    @Test
    public void testBuscarEntregadorPorId() {
        // Cenário: Buscar entregador por ID

        when(entregadorRepository.findById(1L)).thenReturn(Optional.of(entregador)); // Simula a busca de um entregador existente

        Entregador entregadorBuscado = entregadorService.buscarEntregadorPorId(1L);

        assertNotNull(entregadorBuscado);
        assertEquals(1L, entregadorBuscado.getId()); // Verifica se o ID do entregador é o esperado
        verify(entregadorRepository, times(1)).findById(1L);  // Verifica se o método findById foi chamado
    }

    @Test
    public void testBuscarEntregadorPorIdNaoEncontrado() {
        // Cenário: Buscar entregador por ID, mas entregador não existe

        when(entregadorRepository.findById(1L)).thenReturn(Optional.empty()); // Simula a busca de um entregador que não existe

        assertThrows(RuntimeException.class, () -> entregadorService.buscarEntregadorPorId(1L)); // Espera que uma exceção seja lançada
    }
}
