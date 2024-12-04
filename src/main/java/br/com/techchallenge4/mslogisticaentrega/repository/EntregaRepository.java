package br.com.techchallenge4.mslogisticaentrega.repository;

import br.com.techchallenge4.mslogisticaentrega.enums.StatusEntregaEnum;
import br.com.techchallenge4.mslogisticaentrega.model.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    List<Entrega> findByStatusEntrega(StatusEntregaEnum statusEntrega);
}
