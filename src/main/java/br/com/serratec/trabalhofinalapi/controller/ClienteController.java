package br.com.serratec.trabalhofinalapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.trabalhofinalapi.dto.ClienteRequestDTO;
import br.com.serratec.trabalhofinalapi.model.Cliente;
import br.com.serratec.trabalhofinalapi.service.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired 
    private ClienteService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente inserir(@Valid @RequestBody ClienteRequestDTO dto){
        return service.inserir(dto);
    }

    @PutMapping("{id}")
    public ResponseEntity<Cliente> alterar(@RequestBody ClienteRequestDTO dto, @PathVariable Long id){
        return ResponseEntity.ok(service.alterar(id, dto));
    }

    @GetMapping("/paginas")
    public Page<Cliente> listarPorPagina(@PageableDefault(size = 6, page=0, direction = Direction.ASC) Pageable pageable){
        return service.listarPorPagina(pageable);
    }

    @GetMapping("/nome")
    public Page<Cliente> listarPorNome(Pageable pageable,@RequestParam(defaultValue = "") String nome){
        return service.listarPorNome(pageable, nome);
    }
}
