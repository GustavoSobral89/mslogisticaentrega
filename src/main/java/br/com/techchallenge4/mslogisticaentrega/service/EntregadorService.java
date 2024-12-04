package br.com.techchallenge4.mslogisticaentrega.service;

import br.com.techchallenge4.mslogisticaentrega.enums.StatusEntregadorEnum;
import br.com.techchallenge4.mslogisticaentrega.model.Entregador;
import br.com.techchallenge4.mslogisticaentrega.repository.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntregadorService {

    @Autowired
    private EntregadorRepository entregadorRepository;

    // Método para criar um novo entregador com status DISPONÍVEL por padrão
    public Entregador criarEntregador(Entregador entregador) {
        entregador.setStatusEntregador(StatusEntregadorEnum.DISPONIVEL); // Status inicial como DISPONÍVEL
        return entregadorRepository.save(entregador);
    }

    // Método para listar todos os entregadores
    public List<Entregador> listarEntregadores() {
        return entregadorRepository.findAll();
    }

    // Método para buscar entregador por id
    public Entregador buscarEntregadorPorId(Long id) {
        return entregadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entregador não encontrado com o ID: " + id));
    }
}