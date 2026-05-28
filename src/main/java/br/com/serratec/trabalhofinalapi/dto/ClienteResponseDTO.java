package br.com.serratec.trabalhofinalapi.dto;

public record ClienteResponseDTO(
        String nome,
        String telefone,
        String email,
        String cpf) {

}
