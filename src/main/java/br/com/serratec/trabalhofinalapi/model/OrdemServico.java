package br.com.serratec.trabalhofinalapi.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.serratec.trabalhofinalapi.enums.StatusServico;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private StatusServico status;
    private Double valorTotal;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "data_agendamento")
    private LocalDateTime dataAgendamento;

    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExecucaoServico> servicos = new ArrayList<>();

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Veiculo veiculo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusServico getStatus() {
        return status;
    }

    public void setStatus(StatusServico status) {
        this.status = status;
    }

    public Double getValorTotal() {
        double total = 0.0;
        if (servicos != null) {
            for (ExecucaoServico execucaoServico : servicos) {
                if (execucaoServico != null && execucaoServico.getSubTotal() != null) {
                    total += execucaoServico.getSubTotal();
                }
            }
        }
        return total;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(LocalDateTime dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public List<ExecucaoServico> getServicos() {
        return servicos;
    }

    public void setServicos(List<ExecucaoServico> servicos) {
        this.servicos = servicos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

}
