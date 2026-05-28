package br.com.serratec.trabalhofinalapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.trabalhofinalapi.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento,Long> {

}
