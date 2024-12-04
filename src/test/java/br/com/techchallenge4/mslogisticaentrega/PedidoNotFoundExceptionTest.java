package br.com.techchallenge4.mslogisticaentrega;

import br.com.techchallenge4.mslogisticaentrega.exception.PedidoNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PedidoNotFoundExceptionTest {

    @Test
    public void testPedidoNotFoundException() {
        // Dado um ID de pedido
        Long pedidoId = 123L;

        // Quando a exceção é lançada
        PedidoNotFoundException exception = assertThrows(PedidoNotFoundException.class, () -> {
            throw new PedidoNotFoundException(pedidoId);
        });

        // Então a mensagem da exceção deve ser correta
        assertEquals("Pedido não encontrado com o ID: " + pedidoId, exception.getMessage());
    }
}