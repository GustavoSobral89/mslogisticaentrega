package br.com.techchallenge4.mslogisticaentrega.exception;

public class PedidoNotFoundException extends RuntimeException {

    public PedidoNotFoundException(Long id) {
        super("Pedido não encontrado com o ID: " + id);
    }
}
