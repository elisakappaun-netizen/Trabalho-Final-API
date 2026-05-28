package br.com.serratec.trabalhofinalapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String telefone;
    private String email;
    private String dataNascimento;
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Cadastro Realizado, Abaixo segue as suas Informações: \n\n" + "Nome: " + nome + "\nTel: " + telefone
                + "\nEmail: " + email
                + "\nDN: " + dataNascimento + "\nCPF: " + cpf + "\n\nEndereço \n" + endereco;
    }

    public String msgAtualizacao() {
        return "Foi Feito uma Alteração no seu Cadastro, Abaixo segue as Novas Informações: \n\n" + "Nome: " + nome
                + "\nTel: " + telefone + "\nEmail: " + email
                + "\nDN: " + dataNascimento + "\nCPF: " + cpf + "\n\nEndereço \n" + endereco;

    }

}
