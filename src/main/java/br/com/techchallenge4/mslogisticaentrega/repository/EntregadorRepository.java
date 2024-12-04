package br.com.techchallenge4.mslogisticaentrega.repository;

import br.com.techchallenge4.mslogisticaentrega.enums.StatusEntregadorEnum;
import br.com.techchallenge4.mslogisticaentrega.model.Entregador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntregadorRepository extends JpaRepository<Entregador, Long> {
    // Método para buscar entregador pelo status
    List<Entregador> findByStatusEntregador(StatusEntregadorEnum statusEntregador);
}
