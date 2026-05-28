package br.com.serratec.trabalhofinalapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.trabalhofinalapi.dto.OrdemServicoRequestDTO;
import br.com.serratec.trabalhofinalapi.dto.OrdemServicoResponseDTO;
import br.com.serratec.trabalhofinalapi.model.Cliente;
import br.com.serratec.trabalhofinalapi.model.OrdemServico;
import br.com.serratec.trabalhofinalapi.service.OrdemServicoServices;

@RestController
@RequestMapping("/os")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoServices ordemServicoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServico inserir(@RequestBody OrdemServicoRequestDTO dto){
        return ordemServicoService.inserir(dto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrdemServico alterar(@RequestBody OrdemServicoRequestDTO dto, @PathVariable Long id){
        return ordemServicoService.alterar(id, dto);
    }

    @GetMapping("{id}")
    public OrdemServicoResponseDTO buscarNumeroOS(@PathVariable Long id){
        return ordemServicoService.buscarPorId(id);
    }

        @GetMapping("/paginas")
    public Page<OrdemServico> listarPorPagina(@PageableDefault(size = 6, page=0, direction = Direction.ASC) Pageable pageable){
        return ordemServicoService.listarPorPagina(pageable);
    }
}
