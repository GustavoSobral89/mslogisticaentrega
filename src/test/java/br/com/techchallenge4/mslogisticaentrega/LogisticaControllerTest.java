package br.com.techchallenge4.mslogisticaentrega;

import br.com.techchallenge4.mslogisticaentrega.controller.LogisticaController;
import br.com.techchallenge4.mslogisticaentrega.enums.StatusEntregaEnum;
import br.com.techchallenge4.mslogisticaentrega.enums.StatusEntregadorEnum;
import br.com.techchallenge4.mslogisticaentrega.model.Entrega;
import br.com.techchallenge4.mslogisticaentrega.model.Entregador;
import br.com.techchallenge4.mslogisticaentrega.service.LogisticaService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LogisticaControllerTest {

    @Mock
    private LogisticaService logisticaService;

    @InjectMocks
    private LogisticaController logisticaController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(logisticaController).build();
        objectMapper = new ObjectMapper();
    }

    // Testando o sucesso na criação de uma entrega
    @Test
    public void testCriarEntrega_Sucesso() throws Exception {
        // Dados mockados
        Entregador entregador = new Entregador(1L, "John Doe", "MOTO", StatusEntregadorEnum.DISPONIVEL);
        Entrega entrega = new Entrega(1L, entregador, StatusEntregaEnum.CRIADA,
                "Rua Fictícia, 123", 12.5, 30L, "Aguardando");

        // Simulando que o serviço retorna a entrega criada com sucesso
        when(logisticaService.criarEntrega(any(Entrega.class))).thenReturn(entrega);

        // Simulando a requisição POST
        mockMvc.perform(MockMvcRequestBuilders.post("/logistica/entrega")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrega)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusEntrega").value("CRIADA"));

        // Verificando se o método foi chamado corretamente
        verify(logisticaService).criarEntrega(any(Entrega.class));
    }

    // Testando falha ao tentar criar uma entrega
    @Test
    public void testCriarEntrega_Falha() throws Exception {
        // Dados mockados
        Entregador entregador = new Entregador(1L, "John Doe", "MOTO", StatusEntregadorEnum.DISPONIVEL);
        Entrega entrega = new Entrega(1L, entregador, StatusEntregaEnum.CRIADA,
                "Rua Fictícia, 123", 12.5, 30L, "Aguardando");

        // Simulando que o serviço lança uma exceção quando ocorre erro
        when(logisticaService.criarEntrega(any(Entrega.class))).thenThrow(new RuntimeException("Erro ao criar entrega"));

        // Simulando requisição POST
        mockMvc.perform(MockMvcRequestBuilders.post("/logistica/entrega")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrega)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Verificando se o método foi chamado corretamente
        verify(logisticaService).criarEntrega(any(Entrega.class));
    }

    // Testando o sucesso ao iniciar uma entrega
    @Test
    public void testIniciarEntrega_Sucesso() throws Exception {
        // Dados mockados
        Entregador entregador = new Entregador(1L, "John Doe", "MOTO", StatusEntregadorEnum.EM_ROTAS);
        Entrega entrega = new Entrega(1L, entregador, StatusEntregaEnum.EM_TRANSITO,
                "Rua Fictícia, 123", 12.5, 30L, "Em trânsito");

        // Simulando que o serviço retorna a entrega com status atualizado
        when(logisticaService.iniciarEntrega(1L)).thenReturn(entrega);

        // Simulando requisição PUT
        mockMvc.perform(MockMvcRequestBuilders.put("/logistica/entrega/iniciar/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusEntrega").value("EM_TRANSITO"));

        // Verificando se o método foi chamado corretamente
        verify(logisticaService).iniciarEntrega(1L);
    }

    // Testando a falha ao tentar iniciar uma entrega que não existe
    @Test
    public void testIniciarEntrega_NaoEncontrado() throws Exception {
        // Simulando que o serviço lança uma exceção quando não encontra a entrega
        when(logisticaService.iniciarEntrega(1L)).thenThrow(new RuntimeException("Entrega não encontrada"));

        // Simulando requisição PUT
        mockMvc.perform(MockMvcRequestBuilders.put("/logistica/entrega/iniciar/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        // Verificando se o método foi chamado corretamente
        verify(logisticaService).iniciarEntrega(1L);
    }

    // Testando o sucesso ao finalizar uma entrega
    @Test
    public void testFinalizarEntrega_Sucesso() throws Exception {
        // Dados mockados
        Entregador entregador = new Entregador(1L, "John Doe", "MOTO", StatusEntregadorEnum.INDISPONIVEL);
        Entrega entrega = new Entrega(1L, entregador, StatusEntregaEnum.ENTREGUE,
                "Rua Fictícia, 123", 12.5, 30L, "Entregue");

        // Simulando que o serviço retorna a entrega com status finalizado
        when(logisticaService.finalizarEntrega(1L)).thenReturn(entrega);

        // Simulando requisição PUT
        mockMvc.perform(MockMvcRequestBuilders.put("/logistica/entrega/finalizar/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusEntrega").value("ENTREGUE"));

        // Verificando se o método foi chamado corretamente
        verify(logisticaService).finalizarEntrega(1L);
    }

    // Testando a falha ao tentar finalizar uma entrega que não existe
    @Test
    public void testFinalizarEntrega_NaoEncontrado() throws Exception {
        // Simulando que o serviço lança uma exceção quando não encontra a entrega
        when(logisticaService.finalizarEntrega(1L)).thenThrow(new RuntimeException("Entrega não encontrada"));

        // Simulando requisição PUT
        mockMvc.perform(MockMvcRequestBuilders.put("/logistica/entrega/finalizar/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        // Verificando se o método foi chamado corretamente
        verify(logisticaService).finalizarEntrega(1L);
    }
}
