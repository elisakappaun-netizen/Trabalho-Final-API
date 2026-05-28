package br.com.serratec.trabalhofinalapi.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.trabalhofinalapi.model.OrdemServico;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long>{

    boolean existsByDataAgendamento(LocalDateTime dataAgendamento);

}
