package br.com.techchallenge4.mslogisticaentrega.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDTO {

    private Long produtoId;  // ID do produto no pedido
    private Integer quantidade;  // Quantidade do produto no pedido
    private BigDecimal preco;  // Preço unitário do produto
}