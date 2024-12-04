package br.com.techchallenge4.mslogisticaentrega.model;

import br.com.techchallenge4.mslogisticaentrega.enums.StatusEntregaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Usando PedidoDTO em vez de Pedido
    private Long pedidoId;  // ID do pedido associado à entrega

    @ManyToOne
    @JoinColumn(name = "entregador_id")
    private Entregador entregador;  // Relaciona com o Entregador

    @Enumerated(EnumType.STRING)
    private StatusEntregaEnum statusEntrega; // Status da entrega

    private String enderecoDestino;  // Endereço para entrega
    private Double distancia;  // Distância estimada para entrega
    private Long tempoEstimado;  // Tempo estimado de entrega (em minutos)

    // Para as atualizações de rastreamento
    private String rastreamentoStatus;

    public Entrega(Long pedidoId, Entregador entregador, StatusEntregaEnum statusEntrega,
                   String enderecoDestino, Double distancia, Long tempoEstimado,
                   String rastreamentoStatus) {
        this.pedidoId = pedidoId;
        this.entregador = entregador;
        this.statusEntrega = statusEntrega;
        this.enderecoDestino = enderecoDestino;
        this.distancia = distancia;
        this.tempoEstimado = tempoEstimado;
        this.rastreamentoStatus = rastreamentoStatus;
    }
}
