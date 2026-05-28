package br.com.serratec.trabalhofinalapi.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.trabalhofinalapi.dto.ExecucaoServicoRequestDTO;
import br.com.serratec.trabalhofinalapi.dto.OrdemServicoRequestDTO;
import br.com.serratec.trabalhofinalapi.handler.ClienteException;
import br.com.serratec.trabalhofinalapi.handler.OrdemServicoException;
import br.com.serratec.trabalhofinalapi.handler.VeiculoException;
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

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    @Transactional
    public OrdemServico inserir(OrdemServicoRequestDTO dto) {
        LocalDateTime dataAgendamento;
        try {
            dataAgendamento = LocalDateTime.parse(dto.dataAgendamento(), FORMATTER);
        } catch (DateTimeParseException ex) {
            throw new OrdemServicoException("Data de agendamento inválida. Use o formato ISO-8601: yyyy-MM-dd'T'HH:mm");
        }

        if (dataAgendamento.isBefore(LocalDateTime.now())) {
            throw new OrdemServicoException("A data de agendamento deve ser no futuro.");
        }

        if (repository.existsByDataAgendamento(dataAgendamento)) {
            throw new OrdemServicoException("Horário indisponível. Escolha outro horário.");
        }

        Cliente cliente = clienteRepository.findById(dto.id_cliente())
                .orElseThrow(() -> new ClienteException("Cliente não encontrado!"));

        Veiculo veiculo = veiculoRepository.findById(dto.id_veiculo())
                .orElseThrow(() -> new VeiculoException("Veículo não encontrado!"));

        OrdemServico ordemServico = new OrdemServico();
        ordemServico.setCliente(cliente);
        ordemServico.setVeiculo(veiculo);
        ordemServico.setStatus(dto.status());
        ordemServico.setDataAgendamento(dataAgendamento);

        List<ExecucaoServico> listaExecucoes = new ArrayList<>();
        if (dto.execucoes() != null) {
            for (ExecucaoServicoRequestDTO execucaoDto : dto.execucoes()) {
                var servico = servicoService.buscar(execucaoDto.getServico().getId());

                ExecucaoServico execucaoServico = new ExecucaoServico();
                execucaoServico.setDesconto(execucaoDto.getDesconto() == null ? 0.0 : execucaoDto.getDesconto());
                execucaoServico.setQuantidade(execucaoDto.getQuantidade() == null ? 1 : execucaoDto.getQuantidade());
                execucaoServico.setServico(servico);
                execucaoServico.setOrdemServico(ordemServico);
                execucaoServico.setSubTotal(servico.getValor() * execucaoServico.getQuantidade() - execucaoServico.getDesconto());
                listaExecucoes.add(execucaoServico);
            }
        }

        ordemServico.setServicos(listaExecucoes);
        return repository.save(ordemServico);
    }

    public boolean estaDisponivel(String dataAgendamento) {
        try {
            LocalDateTime data = LocalDateTime.parse(dataAgendamento, FORMATTER);
            return !repository.existsByDataAgendamento(data);
        } catch (DateTimeParseException ex) {
            throw new OrdemServicoException("Data de agendamento inválida. Use o formato ISO-8601: yyyy-MM-dd'T'HH:mm");
        }
    }

}
