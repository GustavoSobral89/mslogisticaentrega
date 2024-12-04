package br.com.techchallenge4.mslogisticaentrega;

import br.com.techchallenge4.mslogisticaentrega.client.PedidoClient;
import br.com.techchallenge4.mslogisticaentrega.enums.StatusEntregaEnum;
import br.com.techchallenge4.mslogisticaentrega.enums.StatusEntregadorEnum;
import br.com.techchallenge4.mslogisticaentrega.model.Entrega;
import br.com.techchallenge4.mslogisticaentrega.model.Entregador;
import br.com.techchallenge4.mslogisticaentrega.repository.EntregaRepository;
import br.com.techchallenge4.mslogisticaentrega.repository.EntregadorRepository;
import br.com.techchallenge4.mslogisticaentrega.service.LogisticaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LogisticaServiceTest {

    @Autowired
    private LogisticaService logisticaService;

    @MockBean
    private EntregaRepository entregaRepository;

    @MockBean
    private EntregadorRepository entregadorRepository;

    @MockBean
    private PedidoClient pedidoClient;

    @Mock
    private Entregador entregador;

    @Mock
    private Entrega entrega;

    @BeforeEach
    public void setUp() {
        // Inicializa os mocks antes de cada teste
        entregador = new Entregador(1L, "João", "Moto", StatusEntregadorEnum.DISPONIVEL);
        entrega = new Entrega(1L, entregador, StatusEntregaEnum.CRIADA, "Rua 123", 10.0, 30L, "Entrega sendo preparada");
    }

    @Test
    public void testCriarEntrega() {
        // Cenário: Criar uma nova entrega

        when(entregadorRepository.findByStatusEntregador(StatusEntregadorEnum.DISPONIVEL))
                .thenReturn(java.util.Collections.singletonList(entregador)); // Retorna um entregador disponível
        when(entregaRepository.save(any(Entrega.class))).thenReturn(entrega); // Retorna a entrega salva

        Entrega entregaCriada = logisticaService.criarEntrega(entrega);

        assertNotNull(entregaCriada);
        assertEquals(StatusEntregaEnum.CRIADA, entregaCriada.getStatusEntrega());
        assertEquals(StatusEntregadorEnum.ALOCADO, entregaCriada.getEntregador().getStatusEntregador());

        verify(entregadorRepository, times(1)).save(entregador);  // Verifica se o status do entregador foi salvo
        verify(entregaRepository, times(1)).save(entrega);  // Verifica se a entrega foi salva
    }

    @Test
    public void testIniciarEntrega() {
        // Cenário: Iniciar uma entrega existente

        when(entregaRepository.findById(1L)).thenReturn(Optional.of(entrega)); // Simula a busca de uma entrega
        when(entregadorRepository.findById(1L)).thenReturn(Optional.of(entregador)); // Simula a busca de um entregador
        when(entregaRepository.save(any(Entrega.class))).thenReturn(entrega); // Simula salvar a entrega

        Entrega entregaIniciada = logisticaService.iniciarEntrega(1L);

        assertNotNull(entregaIniciada);
        assertEquals(StatusEntregaEnum.EM_TRANSITO, entregaIniciada.getStatusEntrega());
        assertEquals(StatusEntregadorEnum.EM_ROTAS, entregaIniciada.getEntregador().getStatusEntregador());

        verify(entregadorRepository, times(1)).save(entregador);  // Verifica se o status do entregador foi atualizado
        verify(entregaRepository, times(1)).save(entrega);  // Verifica se a entrega foi salva
    }

    @Test
    public void testFinalizarEntrega() {
        // Cenário: Finalizar uma entrega existente

        when(entregaRepository.findById(1L)).thenReturn(Optional.of(entrega)); // Simula a busca de uma entrega
        when(entregadorRepository.findById(1L)).thenReturn(Optional.of(entregador)); // Simula a busca de um entregador
        when(entregaRepository.save(any(Entrega.class))).thenReturn(entrega); // Simula salvar a entrega

        Entrega entregaFinalizada = logisticaService.finalizarEntrega(1L);

        assertNotNull(entregaFinalizada);
        assertEquals(StatusEntregaEnum.ENTREGUE, entregaFinalizada.getStatusEntrega());
        assertEquals(StatusEntregadorEnum.INDISPONIVEL, entregaFinalizada.getEntregador().getStatusEntregador());

        verify(entregadorRepository, times(1)).save(entregador);  // Verifica se o status do entregador foi atualizado
        verify(entregaRepository, times(1)).save(entrega);  // Verifica se a entrega foi salva
    }

    @Test
    public void testEntregaNaoEncontrada() {
        // Cenário: Tentando iniciar ou finalizar uma entrega que não existe

        when(entregaRepository.findById(1L)).thenReturn(Optional.empty()); // Simula a entrega não encontrada

        assertThrows(RuntimeException.class, () -> logisticaService.iniciarEntrega(1L));
        assertThrows(RuntimeException.class, () -> logisticaService.finalizarEntrega(1L));
    }
}
