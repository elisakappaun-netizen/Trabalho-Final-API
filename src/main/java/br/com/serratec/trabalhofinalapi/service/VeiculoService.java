package br.com.serratec.trabalhofinalapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.serratec.trabalhofinalapi.dto.VeiculoRequestDTO;
import br.com.serratec.trabalhofinalapi.repository.ClienteRepository;
import br.com.serratec.trabalhofinalapi.repository.VeiculoRepository;

import br.com.serratec.trabalhofinalapi.handler.ClienteException;
import br.com.serratec.trabalhofinalapi.handler.VeiculoException;
import br.com.serratec.trabalhofinalapi.model.Cliente;
import br.com.serratec.trabalhofinalapi.model.Veiculo;

@Service
public class VeiculoService {
    
    @Autowired
    private VeiculoRepository vrepository;

    @Autowired
    private ClienteRepository repository;

    public Veiculo inserirVeiculo(VeiculoRequestDTO dto) {
        Optional<Cliente> opt_cliente = repository.findById(dto.id_cliente());

        Cliente cliente;
        
        if(opt_cliente.isPresent()) {
            cliente = opt_cliente.get();
        }else{
            throw new ClienteException("Cliente não encontrado.");
        }

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(dto.placa());
        veiculo.setMarca(dto.marca());
        veiculo.setModelo(dto.modelo());
        veiculo.setCor(dto.cor());
        veiculo.setAno(dto.ano());
        veiculo.setCliente(cliente);
        return vrepository.save(veiculo);
    }

    public Veiculo alterarVeiculo(VeiculoRequestDTO dto, Long id) {
        Optional<Veiculo> opt_veiculo = vrepository.findById(id);

        if(opt_veiculo.isPresent()) {
            Optional<Cliente> opt_cliente = repository.findById(dto.id_cliente());
            Cliente cliente;

            if(opt_cliente.isPresent()) {
                cliente = opt_cliente.get();
            }else{
                throw new ClienteException("Cliente não encontrado.");
            }

            Veiculo veiculo = new Veiculo();
            veiculo.setPlaca(dto.placa());
            veiculo.setMarca(dto.marca());
            veiculo.setModelo(dto.modelo());
            veiculo.setCor(dto.cor());
            veiculo.setAno(dto.ano());
            veiculo.setCliente(cliente);
            return vrepository.save(veiculo);
        }
        throw new VeiculoException("Veículo não encontrado.");
    }

    public Page<Veiculo> listarPorPagina(Pageable pageable) {
        return vrepository.findAll(pageable);
    }

    public Page<Veiculo> listarPorMarca(Pageable pageable, String marca) {
        return vrepository.findByMarcaContaining(pageable, marca);
    }
}
