package br.com.techchallenge4.mslogisticaentrega.model;

import br.com.techchallenge4.mslogisticaentrega.enums.StatusEntregadorEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entregador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String veiculo;  // Veículo usado pelo entregador
    @Enumerated(EnumType.STRING)
    private StatusEntregadorEnum statusEntregador; // Status do entregador (disponível, em rota, indisponível)
}
