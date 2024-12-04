package br.com.techchallenge4.mslogisticaentrega.service;

import br.com.techchallenge4.mslogisticaentrega.client.PedidoClient;
import br.com.techchallenge4.mslogisticaentrega.enums.StatusEntregaEnum;
import br.com.techchallenge4.mslogisticaentrega.enums.StatusEntregadorEnum;
import br.com.techchallenge4.mslogisticaentrega.exception.PedidoNotFoundException;
import br.com.techchallenge4.mslogisticaentrega.model.Entrega;
import br.com.techchallenge4.mslogisticaentrega.model.Entregador;
import br.com.techchallenge4.mslogisticaentrega.repository.EntregaRepository;
import br.com.techchallenge4.mslogisticaentrega.repository.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogisticaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private PedidoClient client;

    // Passo 1: Criar a entrega
    public Entrega criarEntrega(Entrega entrega) {

        Entregador entregador = entregadorRepository.findByStatusEntregador(StatusEntregadorEnum.DISPONIVEL).get(0);

        // Criação da entrega com status AGUARDANDO_ATRIBUICAO
        Entrega entregaSalvar = new Entrega(entrega.getPedidoId(), entregador, StatusEntregaEnum.CRIADA, entrega.getEnderecoDestino(),
                entrega.getDistancia(), entrega.getTempoEstimado(), "Entrega sendo preparada");

        // Atualiza o status do entregador para "Em rota"
        entregador.setStatusEntregador(StatusEntregadorEnum.ALOCADO);
        entregadorRepository.save(entregador);

        return entregaRepository.save(entregaSalvar);
    }

    // Passo 2: Iniciar a entrega
    public Entrega iniciarEntrega(Long entregaId) {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new RuntimeException("Entrega não encontrada"));

        // Atualiza o status da entrega para EM_TRANSITO
        entrega.setStatusEntrega(StatusEntregaEnum.EM_TRANSITO);
        entrega.setRastreamentoStatus("Pedido saiu para entrega");

        // Encontra o entregador e altera o status para "Em rota"
        Entregador entregador = entregadorRepository.findById(entrega.getEntregador().getId())
                .orElseThrow(() -> new RuntimeException("Entregador não encontrado"));

        entregador.setStatusEntregador(StatusEntregadorEnum.EM_ROTAS);
        entregadorRepository.save(entregador);

        return entregaRepository.save(entrega);
    }

    // Passo 3: Finalizar a entrega
    public Entrega finalizarEntrega(Long entregaId) {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new RuntimeException("Entrega não encontrada"));

        // Atualiza o status da entrega para ENTREGUE
        entrega.setStatusEntrega(StatusEntregaEnum.ENTREGUE);
        entrega.setRastreamentoStatus("Entrega finalizada!");

        // Encontra o entregador e atualiza o status para "Indisponível"
        Entregador entregador = entregadorRepository.findById(entrega.getEntregador().getId())
                .orElseThrow(() -> new RuntimeException("Entregador não encontrado"));

        entregador.setStatusEntregador(StatusEntregadorEnum.INDISPONIVEL);  // Indisponível após finalização
        entregadorRepository.save(entregador);

        return entregaRepository.save(entrega);
    }
}