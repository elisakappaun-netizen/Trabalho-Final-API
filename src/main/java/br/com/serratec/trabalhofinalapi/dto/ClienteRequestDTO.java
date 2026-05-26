package br.com.serratec.trabalhofinalapi.dto;

import java.util.UUID;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClienteRequestDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Nome obrigatório!")
    private String nome;

    @NotBlank(message = "Telefone obrigatório")
    @Digits(integer = 11, fraction = 0, message = "Telefone deve conter apenas números e no máximo 11 digitos")
    private String telefone;

    @NotBlank(message = "Email obrigatório!")
    @Email(message = "Insira um e-mail válido!")
    private String email;

    @NotBlank(message = "Informe sua data de nascimento! AAAA-MM-DD")
    @Size(min = 10, max = 10, message = "Deve conter 10 digitos!")
    private String dataNascimento;

    @NotBlank(message = "CPF obrigatório!")
    @CPF(message = "Informe um CPF válido!")
    private String cpf;

    private String cep;

    public ClienteRequestDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

}
