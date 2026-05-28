package br.com.serratec.trabalhofinalapi.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.serratec.trabalhofinalapi.config.MailConfig;
import br.com.serratec.trabalhofinalapi.dto.ClienteRequestDTO;
import br.com.serratec.trabalhofinalapi.handler.ClienteException;
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
        Optional<Endereco> optEndereco = eRepository.findByCep(dto.getCep());

        Endereco endereco;

        if (optEndereco.isPresent()) {
            endereco = optEndereco.get();
        } else {
            try {
                RestTemplate restTemplate = new RestTemplate();
                String url = "https://viacep.com.br/ws/" + dto.getCep() + "/json/";
                Endereco enderecoViaCep = restTemplate.getForObject(url, Endereco.class);
                if (enderecoViaCep == null) {
                    throw new EnderecoException("Cep não encontrado!");
                }

                enderecoViaCep.setCep(enderecoViaCep.getCep().replaceAll("-", ""));

                endereco = eRepository.save(enderecoViaCep);
            } catch (Exception e) {
                throw new EnderecoException("Erro ao consultar o CEP: " + dto.getCep());
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

        return repository.save(cliente);
    }

    public Cliente alterar(UUID id, ClienteRequestDTO dto) {
        Optional<Cliente> findCliente = repository.findById(id);

        if (findCliente.isPresent()) {
            Cliente cliente = findCliente.get();

            Optional<Endereco> optEndereco = eRepository.findByCep(dto.getCep());

            Endereco endereco;

            if (optEndereco.isPresent()) {
                endereco = optEndereco.get();
            } else {
                try {
                    RestTemplate restTemplate = new RestTemplate();
                    String url = "https://viacep.com.br/ws/" + dto.getCep() + "/json/";
                    Endereco enderecoViaCep = restTemplate.getForObject(url, Endereco.class);
                    if (enderecoViaCep == null) {
                        throw new EnderecoException("Cep não encontrado!");
                    }

                    enderecoViaCep.setCep(enderecoViaCep.getCep().replaceAll("-", ""));

                    endereco = eRepository.save(enderecoViaCep);
                } catch (Exception e) {
                    throw new EnderecoException("Erro ao consultar o CEP: " + dto.getCep());
                }
            }

            cliente.setNome(dto.getNome());
            cliente.setTelefone(dto.getTelefone());
            cliente.setEmail(dto.getEmail());
            cliente.setDataNascimento(dto.getDataNascimento());
            cliente.setCpf(dto.getCpf());
            cliente.setEndereco(endereco);

            //mailConfig.enviarEmail(cliente.msgAtualizacao(),dto.getEmail(),"Seu Cadastro na | Oficina Tech | foi Alterado com Sucesso!");

            return repository.save(cliente);
        }

        throw new ClienteException("Cliente não encontrado!");
    }

    public Page<Cliente> listarPorPagina(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Cliente> listarPorNome(Pageable pageable, String nome) {
        return repository.findByNomeContaining(pageable, nome);
    }

}
