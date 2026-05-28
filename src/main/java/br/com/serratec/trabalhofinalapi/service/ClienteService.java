package br.com.serratec.trabalhofinalapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.serratec.trabalhofinalapi.config.MailConfig;
import br.com.serratec.trabalhofinalapi.dto.ClienteRequestDTO;
import br.com.serratec.trabalhofinalapi.handler.CepInvalidoException;
import br.com.serratec.trabalhofinalapi.handler.ClienteException;
import br.com.serratec.trabalhofinalapi.handler.DadosInvalidosException;
import br.com.serratec.trabalhofinalapi.handler.DatabaseException;
import br.com.serratec.trabalhofinalapi.handler.EnderecoException;
import br.com.serratec.trabalhofinalapi.model.Cliente;
import br.com.serratec.trabalhofinalapi.model.Endereco;
import br.com.serratec.trabalhofinalapi.repository.ClienteRepository;
import br.com.serratec.trabalhofinalapi.repository.EnderecoRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private EnderecoRepository eRepository;

    @Autowired
    private MailConfig mailConfig;

    public Cliente inserir(ClienteRequestDTO dto) {
        validarDadosCliente(dto);
        validarCep(dto.getCep());

        Optional<Endereco> optEndereco = eRepository.findByCep(dto.getCep());

        Endereco endereco;

        if (optEndereco.isPresent()) {
            endereco = optEndereco.get();
        } else {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://viacep.com.br/ws/" + dto.getCep() + "/json/";
            Endereco enderecoViaCep = restTemplate.getForObject(url, Endereco.class);
            if (enderecoViaCep == null || enderecoViaCep.getCep() == null) {
                throw new EnderecoException("Cep não encontrado!");
            }

            enderecoViaCep.setCep(enderecoViaCep.getCep().replaceAll("-", ""));

            try {
                endereco = eRepository.save(enderecoViaCep);
            } catch (DataAccessException ex) {
                throw new DatabaseException("Erro ao salvar endereço.");
            }
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEmail(dto.getEmail());
        cliente.setDataNascimento(dto.getDataNascimento());
        cliente.setCpf(dto.getCpf());
        cliente.setEndereco(endereco);

       // mailConfig.enviarEmail(cliente.toString(),dto.getEmail(),"O seu Cadastro na | Oficina Tech | foi Realizado com Sucesso!");

        try {
            return repository.save(cliente);
        } catch (DataAccessException ex) {
            throw new DatabaseException("Erro ao salvar cliente.");
        }
    }

    public Cliente alterar(Long id, ClienteRequestDTO dto) {
        validarDadosCliente(dto);
        validarCep(dto.getCep());

        Optional<Cliente> findCliente = repository.findById(id);

        if (findCliente.isPresent()) {
            Cliente cliente = findCliente.get();

            Optional<Endereco> optEndereco = eRepository.findByCep(dto.getCep());

            Endereco endereco;

            if (optEndereco.isPresent()) {
                endereco = optEndereco.get();
            } else {
                RestTemplate restTemplate = new RestTemplate();
                String url = "https://viacep.com.br/ws/" + dto.getCep() + "/json/";
                Endereco enderecoViaCep = restTemplate.getForObject(url, Endereco.class);
                if (enderecoViaCep == null || enderecoViaCep.getCep() == null) {
                    throw new EnderecoException("Cep não encontrado!");
                }

                enderecoViaCep.setCep(enderecoViaCep.getCep().replaceAll("-", ""));

                try {
                    endereco = eRepository.save(enderecoViaCep);
                } catch (DataAccessException ex) {
                    throw new DatabaseException("Erro ao salvar endereço.");
                }
            }

            cliente.setNome(dto.getNome());
            cliente.setTelefone(dto.getTelefone());
            cliente.setEmail(dto.getEmail());
            cliente.setDataNascimento(dto.getDataNascimento());
            cliente.setCpf(dto.getCpf());
            cliente.setEndereco(endereco);

            //mailConfig.enviarEmail(cliente.msgAtualizacao(),dto.getEmail(),"Seu Cadastro na | Oficina Tech | foi Alterado com Sucesso!");

            try {
                return repository.save(cliente);
            } catch (DataAccessException ex) {
                throw new DatabaseException("Erro ao atualizar cliente.");
            }
        }

        throw new ClienteException("Cliente não encontrado!");
    }

    public Page<Cliente> listarPorPagina(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Cliente> listarPorNome(Pageable pageable, String nome) {
        return repository.findByNomeContaining(pageable, nome);
    }

    private void validarCep(String cep) {
        if (cep == null || cep.isBlank()) {
            throw new CepInvalidoException("CEP é obrigatório.");
        }

        String cepNumerico = cep.replaceAll("\\D", "");
        if (cepNumerico.length() != 8) {
            throw new CepInvalidoException("CEP inválido. Informe 8 dígitos.");
        }
    }

    private void validarDadosCliente(ClienteRequestDTO dto) {
        if (dto == null) {
            throw new DadosInvalidosException("Dados do cliente não informados.");
        }

        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new DadosInvalidosException("Nome do cliente é obrigatório.");
        }
    }

}
