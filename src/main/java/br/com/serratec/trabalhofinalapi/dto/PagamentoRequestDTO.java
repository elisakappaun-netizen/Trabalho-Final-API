package br.com.serratec.trabalhofinalapi.dto;

import java.math.BigDecimal;

import br.com.serratec.trabalhofinalapi.enums.FormaPagamento;

public record PagamentoRequestDTO(
        Long ordemServicoId,
        BigDecimal valorPago,
        FormaPagamento formaPagamento
) {}