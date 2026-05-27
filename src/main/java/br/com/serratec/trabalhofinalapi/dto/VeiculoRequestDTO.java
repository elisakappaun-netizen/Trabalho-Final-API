package br.com.serratec.trabalhofinalapi.dto;

import java.util.UUID;

public record VeiculoRequestDTO(String placa, String marca, String modelo, String cor, int ano, UUID id_cliente) {

}
