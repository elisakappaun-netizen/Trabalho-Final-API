package br.com.serratec.trabalhofinalapi.dto;

public record VeiculoResponseDTO(
        String placa,
        String marca,
        String modelo,
        String cor,
        Integer ano) {

}
