package br.com.serratec.trabalhofinalapi.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.trabalhofinalapi.model.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente, UUID>{

    Page<Cliente> findByNomeContaining(Pageable pageable,String nome);
}
