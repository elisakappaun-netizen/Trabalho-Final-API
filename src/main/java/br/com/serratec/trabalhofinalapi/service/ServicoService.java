package br.com.serratec.trabalhofinalapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.trabalhofinalapi.handler.ServicoException;
import br.com.serratec.trabalhofinalapi.model.Servico;
import br.com.serratec.trabalhofinalapi.repository.ServicoRepository;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public Servico inserir(Servico servico){
        return servicoRepository.save(servico);
    }

    public Servico alterar(Long id, Servico servicoAtualizado){
        Optional<Servico> servicos = servicoRepository.findById(id);
        if (servicos.isPresent()) {
            Servico servico = servicos.get();
            servico.setDescricao(servicoAtualizado.getDescricao());
            servico.setTempoEstimado(servicoAtualizado.getTempoEstimado());
            servico.setValor(servicoAtualizado.getValor());
            return servicoRepository.save(servico);
        }throw new ServicoException("Serviço não foi Encontrado!");

    }

    public Servico buscar(Long id){
        Optional<Servico> servico = servicoRepository.findById(id);
        return servico.get();
    }

}
