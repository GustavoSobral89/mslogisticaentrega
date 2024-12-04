package br.com.techchallenge4.mslogisticaentrega.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Long id;
    private Long clienteId;
    private String enderecoDestino;  // Endereço de entrega
    private List<ItemPedidoDTO> itens;  // Itens do pedido, se necessário
    private String status;
    private LocalDateTime dataPedido;
}