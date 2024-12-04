package br.com.techchallenge4.mslogisticaentrega.enums;

import lombok.Getter;

@Getter
public enum StatusEntregadorEnum {
    DISPONIVEL("Disponível"),
    ALOCADO("Alocado"),
    EM_ROTAS("Em rota"),
    INDISPONIVEL("Indisponível");

    private final String descricao;

    StatusEntregadorEnum(String descricao) {
        this.descricao = descricao;
    }

    // Método para obter o enum a partir da descrição
    public static StatusEntregadorEnum fromDescricao(String descricao) {
        for (StatusEntregadorEnum status : StatusEntregadorEnum.values()) {
            if (status.getDescricao().equalsIgnoreCase(descricao)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Descrição inválida: " + descricao);
    }
}
