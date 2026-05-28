package br.com.serratec.trabalhofinalapi.dto;

import java.math.BigDecimal;

public record ExecucaoServicoResponseDTO(
    String descricaoServico,
    Integer quantidade,
    BigDecimal valor,
    BigDecimal desconto,
    BigDecimal subtotal
) {}
