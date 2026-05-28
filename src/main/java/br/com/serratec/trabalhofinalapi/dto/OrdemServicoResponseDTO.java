package br.com.serratec.trabalhofinalapi.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrdemServicoResponseDTO(
        Long id,
        ClienteResponseDTO cliente,
        VeiculoResponseDTO veiculo,
        List<ExecucaoServicoResponseDTO> servicos,
        BigDecimal valorTotal) {
}
