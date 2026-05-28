package br.com.serratec.trabalhofinalapi.dto;

import java.util.List;

import br.com.serratec.trabalhofinalapi.enums.StatusServico;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record OrdemServicoRequestDTO(
        @NotNull(message = "Cliente ID")
        Long clienteId,

        @NotNull(message = "Veículo ID")
        Long veiculoId,

        @NotNull(message = "Status")
        StatusServico status,

        @NotEmpty(message = "Deve conter pelo menos um serviço")
        @Valid
        List<ExecucaoServicoRequestDTO> execucoes
    ){

}