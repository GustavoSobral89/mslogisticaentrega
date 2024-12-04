package br.com.techchallenge4.mslogisticaentrega.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "logistica-service", url = "http://localhost:8081") // URL do microserviço de logística
public interface PedidoClient {

    @GetMapping("/logistica/{id}")  // Endpoint de logística para buscar um pedido específico
    ResponseEntity<Map<String, Object>> getLogistica(@PathVariable("id") Long id);
}