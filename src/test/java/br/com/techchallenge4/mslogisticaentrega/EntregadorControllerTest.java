package br.com.techchallenge4.mslogisticaentrega;

import br.com.techchallenge4.mslogisticaentrega.controller.EntregadorController;
import br.com.techchallenge4.mslogisticaentrega.enums.StatusEntregadorEnum;
import br.com.techchallenge4.mslogisticaentrega.model.Entregador;
import br.com.techchallenge4.mslogisticaentrega.service.EntregadorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EntregadorControllerTest {

    @Mock
    private EntregadorService entregadorService;

    @InjectMocks
    private EntregadorController entregadorController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private Entregador entregador;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(entregadorController).build();
        objectMapper = new ObjectMapper();

        // Inicializando Entregador com dados fictícios
        entregador = new Entregador();
        entregador.setId(1L);
        entregador.setNome("Entregador 1");
        entregador.setVeiculo("Moto");
        entregador.setStatusEntregador(StatusEntregadorEnum.DISPONIVEL);
    }

    @Test
    public void testCriarEntregador_Sucesso() throws Exception {
        // Simulando o serviço para retornar o entregador criado
        when(entregadorService.criarEntregador(any(Entregador.class))).thenReturn(entregador);

        // Simulando requisição POST para criar entregador
        mockMvc.perform(MockMvcRequestBuilders.post("/entregadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entregador)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Entregador 1"));

        // Verificando se o método foi chamado corretamente
        verify(entregadorService).criarEntregador(any(Entregador.class));
    }

    @Test
    public void testCriarEntregador_Falha() throws Exception {
        // Simulando o serviço para lançar uma exceção
        when(entregadorService.criarEntregador(any(Entregador.class))).thenThrow(new RuntimeException());

        // Simulando requisição POST para criar entregador com falha
        mockMvc.perform(MockMvcRequestBuilders.post("/entregadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entregador)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Verificando se o método foi chamado corretamente
        verify(entregadorService).criarEntregador(any(Entregador.class));
    }

    @Test
    public void testListarEntregadores_Sucesso() throws Exception {
        // Dados mockados
        List<Entregador> entregadores = List.of(entregador);
        when(entregadorService.listarEntregadores()).thenReturn(entregadores);

        // Simulando requisição GET para listar entregadores
        mockMvc.perform(MockMvcRequestBuilders.get("/entregadores"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("Entregador 1"));

        // Verificando se o método foi chamado corretamente
        verify(entregadorService).listarEntregadores();
    }

    @Test
    public void testListarEntregadores_Falha() throws Exception {
        // Simulando falha no serviço ao listar entregadores
        when(entregadorService.listarEntregadores()).thenThrow(new RuntimeException("Erro ao recuperar entregadores"));

        // Simulando requisição GET para listar entregadores com falha
        mockMvc.perform(MockMvcRequestBuilders.get("/entregadores"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string("Erro ao recuperar entregadores: Erro ao recuperar entregadores"));

        // Verificando se o método foi chamado corretamente
        verify(entregadorService).listarEntregadores();
    }

    @Test
    public void testBuscarEntregadorPorId_Sucesso() throws Exception {
        // Simulando o serviço para retornar o entregador com o ID 1
        when(entregadorService.buscarEntregadorPorId(1L)).thenReturn(entregador);

        // Simulando requisição GET para buscar entregador por ID
        mockMvc.perform(MockMvcRequestBuilders.get("/entregadores/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Entregador 1"));

        // Verificando se o método foi chamado corretamente
        verify(entregadorService).buscarEntregadorPorId(1L);
    }

    @Test
    public void testBuscarEntregadorPorId_NaoEncontrado() throws Exception {
        // Simulando que não encontra o entregador com o ID 1
        when(entregadorService.buscarEntregadorPorId(1L)).thenThrow(new RuntimeException());

        // Simulando requisição GET para buscar entregador que não existe
        mockMvc.perform(MockMvcRequestBuilders.get("/entregadores/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        // Verificando se o método foi chamado corretamente
        verify(entregadorService).buscarEntregadorPorId(1L);
    }
}