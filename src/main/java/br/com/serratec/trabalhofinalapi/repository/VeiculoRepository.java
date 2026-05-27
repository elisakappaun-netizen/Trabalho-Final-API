package br.com.serratec.trabalhofinalapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.trabalhofinalapi.model.Veiculo;


public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    Page<Veiculo> findByMarcaContaining(Pageable pageable, String marca);
}
