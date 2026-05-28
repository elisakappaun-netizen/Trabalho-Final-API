package br.com.serratec.trabalhofinalapi.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.serratec.trabalhofinalapi.dto.ClienteResponseDTO;
import br.com.serratec.trabalhofinalapi.dto.ExecucaoServicoRequestDTO;
import br.com.serratec.trabalhofinalapi.dto.ExecucaoServicoResponseDTO;
import br.com.serratec.trabalhofinalapi.dto.OrdemServicoRequestDTO;
import br.com.serratec.trabalhofinalapi.dto.OrdemServicoResponseDTO;
import br.com.serratec.trabalhofinalapi.dto.VeiculoResponseDTO;
import br.com.serratec.trabalhofinalapi.handler.ClienteException;
import br.com.serratec.trabalhofinalapi.handler.OrdemServicoException;
import br.com.serratec.trabalhofinalapi.handler.ServicoException;
import br.com.serratec.trabalhofinalapi.handler.VeiculoException;
import br.com.serratec.trabalhofinalapi.model.Cliente;
import br.com.serratec.trabalhofinalapi.model.ExecucaoServico;
import br.com.serratec.trabalhofinalapi.model.OrdemServico;
import br.com.serratec.trabalhofinalapi.model.Servico;
import br.com.serratec.trabalhofinalapi.model.Veiculo;
import br.com.serratec.trabalhofinalapi.repository.ClienteRepository;
import br.com.serratec.trabalhofinalapi.repository.OrdemServicoRepository;
import br.com.serratec.trabalhofinalapi.repository.ServicoRepository;
import br.com.serratec.trabalhofinalapi.repository.VeiculoRepository;

@Service
public class OrdemServicoServices {

        @Autowired
        private OrdemServicoRepository repository;

        @Autowired
        private ClienteRepository clienteRepository;

        @Autowired
        private VeiculoRepository veiculoRepository;

        @Autowired
        private ServicoRepository servicoRepository;

        public OrdemServico inserir(OrdemServicoRequestDTO dto) {

                Cliente cliente = clienteRepository.findById(dto.clienteId())
                                .orElseThrow(() -> new ClienteException("Cliente não encontrado!"));

                Veiculo veiculo = veiculoRepository.findById(dto.veiculoId())
                                .orElseThrow(() -> new VeiculoException("Veículo não encontrado!"));

                OrdemServico os = new OrdemServico();

                os.setCliente(cliente);
                os.setVeiculo(veiculo);
                os.setStatus(dto.status());

                List<ExecucaoServico> execucoes = new ArrayList<>();

                for (ExecucaoServicoRequestDTO execucao : dto.execucoes()) {

                        Servico servico = servicoRepository.findById(execucao.servicoId())
                                        .orElseThrow(() -> new ServicoException("Serviço não encontrado!"));

                        ExecucaoServico execucaoServico = new ExecucaoServico();

                        execucaoServico.setOrdemServico(os);
                        execucaoServico.setServico(servico);
                        execucaoServico.setQuantidade(execucao.quantidade());

                        BigDecimal desconto;

                        if (execucao.desconto() != null) {
                                desconto = execucao.desconto();
                        } else {
                                desconto = BigDecimal.ZERO;
                        }

                        execucaoServico.setDesconto(desconto);

                        BigDecimal subtotal = servico.getValor()
                                        .multiply(BigDecimal.valueOf(execucao.quantidade()))
                                        .subtract(desconto);

                        execucaoServico.setValor(servico.getValor());
                        execucaoServico.setSubtotal(subtotal);

                        execucoes.add(execucaoServico);
                }

                os.setServicos(execucoes);

                return repository.save(os);
        }

        public OrdemServico alterar(Long id, OrdemServicoRequestDTO dto) {
                OrdemServico os = repository.findById(id)
                                .orElseThrow(() -> new OrdemServicoException("Ordem de serviço não encontrada!"));

                Cliente cliente = clienteRepository.findById(dto.clienteId())
                                .orElseThrow(() -> new ClienteException("Cliente não encontrado!"));

                Veiculo veiculo = veiculoRepository.findById(dto.veiculoId())
                                .orElseThrow(() -> new VeiculoException("Veículo não encontrado!"));

                os.setCliente(cliente);
                os.setVeiculo(veiculo);
                os.setStatus(dto.status());

                os.getServicos().clear();

                for (ExecucaoServicoRequestDTO execucao : dto.execucoes()) {

                        Servico servico = servicoRepository.findById(execucao.servicoId())
                                        .orElseThrow(() -> new ServicoException("Serviço não encontrado"));

                        BigDecimal desconto;

                        if (execucao.desconto() != null) {
                                desconto = execucao.desconto();
                        } else {
                                desconto = BigDecimal.ZERO;
                        }

                        BigDecimal subtotal = servico.getValor()
                                        .multiply(BigDecimal.valueOf(execucao.quantidade()))
                                        .subtract(desconto);

                        ExecucaoServico execucaoServico = new ExecucaoServico();

                        execucaoServico.setOrdemServico(os);
                        execucaoServico.setServico(servico);
                        execucaoServico.setQuantidade(execucao.quantidade());
                        execucaoServico.setDesconto(desconto);
                        execucaoServico.setValor(servico.getValor());
                        execucaoServico.setSubtotal(subtotal);

                        os.getServicos().add(execucaoServico);
                }

                return repository.save(os);

        }

        public OrdemServicoResponseDTO buscarPorId(Long id) {

                OrdemServico os = repository.findById(id)
                                .orElseThrow(() -> new OrdemServicoException("OS não encontrada"));

                List<ExecucaoServicoResponseDTO> servicos = os.getServicos().stream()
                                .map(e -> new ExecucaoServicoResponseDTO(
                                                e.getServico().getDescricao(),
                                                e.getQuantidade(),
                                                e.getValor(),
                                                e.getDesconto(),
                                                e.getSubtotal()))
                                .toList();

                ClienteResponseDTO clienteDTO = new ClienteResponseDTO(
                                os.getCliente().getNome(),
                                os.getCliente().getTelefone(),
                                os.getCliente().getEmail(),
                                os.getCliente().getCpf());

                VeiculoResponseDTO veiculoDTO = new VeiculoResponseDTO(
                                os.getVeiculo().getPlaca(),
                                os.getVeiculo().getMarca(),
                                os.getVeiculo().getModelo(),
                                os.getVeiculo().getCor(),
                                os.getVeiculo().getAno());

                BigDecimal total = os.getServicos().stream()
                                .map(ExecucaoServico::getSubtotal)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                return new OrdemServicoResponseDTO(
                                os.getId(),
                                clienteDTO,
                                veiculoDTO,
                                servicos,
                                total);
        }

        public Page<OrdemServico> listarPorPagina(Pageable pageable) {
                return repository.findAll(pageable);
        }

}