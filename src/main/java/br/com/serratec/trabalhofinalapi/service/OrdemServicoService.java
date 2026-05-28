package br.com.serratec.trabalhofinalapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.trabalhofinalapi.dto.ExecucaoServicoRequestDTO;
import br.com.serratec.trabalhofinalapi.dto.OrdemServicoRequestDTO;
import br.com.serratec.trabalhofinalapi.model.Cliente;
import br.com.serratec.trabalhofinalapi.model.ExecucaoServico;
import br.com.serratec.trabalhofinalapi.model.OrdemServico;
import br.com.serratec.trabalhofinalapi.model.Veiculo;
import br.com.serratec.trabalhofinalapi.repository.ClienteRepository;
import br.com.serratec.trabalhofinalapi.repository.OrdemServicoRepository;
import br.com.serratec.trabalhofinalapi.repository.VeiculoRepository;
import jakarta.transaction.Transactional;

@Service
public class OrdemServicoService {

    @Autowired
    private OrdemServicoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ServicoService servicoService;

    @Transactional
    public OrdemServico inserir(OrdemServicoRequestDTO dto) {
        Optional<Cliente> optCliente = clienteRepository.findById(dto.id_cliente());
        Optional<Veiculo> optVeiculo = veiculoRepository.findById(dto.id_veiculo());
        Veiculo veiculo = new Veiculo();
        Cliente cliente = new Cliente();
        if (optVeiculo.isPresent() && optCliente.isPresent()) {

            veiculo = optVeiculo.get();
            cliente = optCliente.get();
        }

        OrdemServico ordemServico = new OrdemServico();
        ordemServico.setCliente(cliente);
        ordemServico.setVeiculo(veiculo);
        ordemServico.setStatus(dto.status());
        ordemServico = repository.save(ordemServico);

        for (ExecucaoServicoRequestDTO execucao : dto.execucoes()) {
            execucao.setOrdemServico(ordemServico);

            execucao.setServico(servicoService.buscar(execucao.getServico().getId()));
        }
        return ordemServico;
    }

}
