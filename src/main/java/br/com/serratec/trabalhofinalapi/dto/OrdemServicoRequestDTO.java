package br.com.serratec.trabalhofinalapi.dto;

import java.util.List;
import java.util.UUID;

import br.com.serratec.trabalhofinalapi.enums.StatusServico;

public record OrdemServicoRequestDTO(StatusServico status, UUID id_cliente, Long id_veiculo, String dataAgendamento, List<ExecucaoServicoRequestDTO> execucoes) {

}
