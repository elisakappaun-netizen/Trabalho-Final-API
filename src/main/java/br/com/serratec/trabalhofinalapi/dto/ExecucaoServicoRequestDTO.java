package br.com.serratec.trabalhofinalapi.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ExecucaoServicoRequestDTO(   
        @NotNull(message = "ID do serviço")
        Long servicoId,

        @NotNull(message = "Quantidade")
        @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
        Integer quantidade,

        @NotNull(message = "Valor")
        @DecimalMin(value = "0", message = "Valor não pode ser negativo")
        BigDecimal valor,

        @NotNull(message = "Desconto")
        @DecimalMin(value = "0", message = "Desconto não pode ser negativo")
        BigDecimal desconto
    ){

}