package br.com.serratec.trabalhofinalapi.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.trabalhofinalapi.dto.PagamentoRequestDTO;
import br.com.serratec.trabalhofinalapi.handler.OrdemServicoException;
import br.com.serratec.trabalhofinalapi.handler.PagamentoException;
import br.com.serratec.trabalhofinalapi.model.OrdemServico;
import br.com.serratec.trabalhofinalapi.model.Pagamento;
import br.com.serratec.trabalhofinalapi.repository.OrdemServicoRepository;
import br.com.serratec.trabalhofinalapi.repository.PagamentoRepository;
import jakarta.transaction.Transactional;

@Service
public class PagamentoService {
    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Transactional
    public Pagamento inserir(PagamentoRequestDTO dto) {

        OrdemServico os = ordemServicoRepository.findById(dto.ordemServicoId())
                .orElseThrow(() -> new OrdemServicoException("OS não encontrada"));

        Pagamento pagamento = new Pagamento();

        pagamento.setOrdemServico(os);
        pagamento.setValorPago(dto.valorPago());
        pagamento.setFormaPagamento(dto.formaPagamento());
        pagamento.setDataPagamento(LocalDate.now());

        return repository.save(pagamento);
    }

    public List<Pagamento> listar() {
        return repository.findAll();
    }

    public Pagamento buscar(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new PagamentoException("Pagamento não encontrado"));
    }

    @Transactional
    public Pagamento alterar(Long id, PagamentoRequestDTO dto) {

        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new PagamentoException("Pagamento não encontrado"));

        pagamento.setValorPago(dto.valorPago());
        pagamento.setFormaPagamento(dto.formaPagamento());

        return repository.save(pagamento);
    }

    public void deletar(Long id) {

        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new PagamentoException("Pagamento não encontrado"));

        repository.delete(pagamento);
    }
}
