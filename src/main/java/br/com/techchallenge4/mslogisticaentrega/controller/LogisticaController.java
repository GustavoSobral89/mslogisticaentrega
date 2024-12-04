package br.com.techchallenge4.mslogisticaentrega.controller;

import br.com.techchallenge4.mslogisticaentrega.model.Entrega;
import br.com.techchallenge4.mslogisticaentrega.service.LogisticaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logistica")
public class LogisticaController {

    @Autowired
    private LogisticaService logisticaService;

    // Passo 1: Criar uma nova entrega
    @Operation(
            summary = "Cria uma nova entrega",
            description = "Cria uma entrega associando o pedido, entregador, e dados de logística."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entrega criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar entrega, entregador indisponível ou dados inválidos")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/entrega")
    public ResponseEntity<Entrega> criarEntrega(@RequestBody Entrega entrega) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(logisticaService.criarEntrega(entrega));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Passo 2: Iniciar a entrega
    @Operation(
            summary = "Inicia a entrega",
            description = "Atualiza o status da entrega para 'Em trânsito' e o status do entregador para 'Em rota'."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrega iniciada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Entrega não encontrada")
    })
    @PutMapping("/entrega/iniciar/{entregaId}")
    public ResponseEntity<Entrega> iniciarEntrega(@PathVariable Long entregaId) {
        try {
            return ResponseEntity.ok(logisticaService.iniciarEntrega(entregaId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Passo 3: Finalizar a entrega
    @Operation(
            summary = "Finaliza a entrega",
            description = "Atualiza o status da entrega para 'Entregue' e o status do entregador para 'Indisponível'."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrega finalizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Entrega não encontrada")
    })
    @PutMapping("/entrega/finalizar/{entregaId}")
    public ResponseEntity<Entrega> finalizarEntrega(@PathVariable Long entregaId) {
        try {
            return ResponseEntity.ok(logisticaService.finalizarEntrega(entregaId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
