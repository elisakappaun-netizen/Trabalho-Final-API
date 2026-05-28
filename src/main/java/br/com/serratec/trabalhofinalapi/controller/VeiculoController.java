package br.com.serratec.trabalhofinalapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.serratec.trabalhofinalapi.dto.VeiculoRequestDTO;
import br.com.serratec.trabalhofinalapi.handler.RegistroNaoEncontradoException;
import br.com.serratec.trabalhofinalapi.model.Veiculo;
import br.com.serratec.trabalhofinalapi.service.VeiculoService;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {
    @Autowired
    private VeiculoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Veiculo inserir(@RequestBody VeiculoRequestDTO dto,
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        AuthValidator.validarToken(authorization);
        return service.inserirVeiculo(dto);
    }

    @PutMapping("{id}")
    public ResponseEntity<Veiculo> alterarVeiculo(@RequestBody VeiculoRequestDTO dto, @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        AuthValidator.validarToken(authorization);
        if (id <= 0) {
            throw new RegistroNaoEncontradoException("Veículo não encontrado.");
        }
        return ResponseEntity.ok(service.alterarVeiculo(dto, id));
    }

    @GetMapping("/paginas")
    public Page<Veiculo> listarPorPagina(
            @PageableDefault(size = 3, page = 0, direction = Direction.ASC) Pageable pageable) {
        return service.listarPorPagina(pageable);
    }

    @GetMapping("/marcas")
    public Page<Veiculo> listarPorMarca(
            Pageable pageable, @RequestParam(defaultValue = "") String marca) {
        return service.listarPorMarca(pageable, marca);
    }
}
