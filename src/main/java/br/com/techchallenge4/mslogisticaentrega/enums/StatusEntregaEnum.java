package br.com.techchallenge4.mslogisticaentrega.enums;

import lombok.Getter;

@Getter
public enum StatusEntregaEnum {
    AGUARDANDO_ATRIBUICAO("Aguardando atribuição"),
    CRIADA("Entrega criada"),
    EM_TRANSITO("Em trânsito"),
    ENTREGUE("Entregue"),
    CANCELADA("Cancelada");

    private final String descricao;

    StatusEntregaEnum(String descricao) {
        this.descricao = descricao;
    }

    // Método para obter o enum a partir da descrição
    public static StatusEntregaEnum fromDescricao(String descricao) {
        for (StatusEntregaEnum status : StatusEntregaEnum.values()) {
            if (status.getDescricao().equalsIgnoreCase(descricao)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Descrição inválida: " + descricao);
    }
}