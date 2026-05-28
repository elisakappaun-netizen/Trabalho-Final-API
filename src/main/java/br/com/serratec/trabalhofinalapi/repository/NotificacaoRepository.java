package br.com.serratec.trabalhofinalapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.trabalhofinalapi.model.Notificacao;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long>{
}
