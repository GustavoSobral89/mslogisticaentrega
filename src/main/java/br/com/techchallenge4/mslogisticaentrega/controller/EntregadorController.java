package br.com.techchallenge4.mslogisticaentrega.controller;

import br.com.techchallenge4.mslogisticaentrega.model.Entregador;
import br.com.techchallenge4.mslogisticaentrega.service.EntregadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entregadores")
public class EntregadorController {

    @Autowired
    private EntregadorService entregadorService;

    // Endpoint para criar um novo entregador
    @Operation(summary = "Cria um novo entregador", description = "Cria um entregador com status DISPONÍVEL por padrão.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entregador criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para a criação do entregador")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Entregador> criarEntregador(@RequestBody Entregador entregador) {
        try {
            return ResponseEntity.ok(entregadorService.criarEntregador(entregador));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Endpoint para listar todos os entregadores
    // Endpoint para listar todos os entregadores
    @Operation(summary = "Lista todos os entregadores", description = "Recupera todos os entregadores cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de entregadores retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao recuperar entregadores")
    })
    @GetMapping
    public ResponseEntity<List<Entregador>> listarEntregadores() {
        return ResponseEntity.ok(entregadorService.listarEntregadores());
    }

    // Tratamento de exceção global para RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        String errorMessage = "Erro ao recuperar entregadores: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    // Endpoint para buscar entregador por ID
    @Operation(summary = "Busca um entregador pelo ID", description = "Recupera um entregador pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entregador encontrado"),
            @ApiResponse(responseCode = "404", description = "Entregador não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Entregador> buscarEntregadorPorId(@PathVariable Long id) {
        try {
            Entregador entregador = entregadorService.buscarEntregadorPorId(id);
            return ResponseEntity.ok(entregador);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
