package br.com.serratec.trabalhofinalapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.trabalhofinalapi.dto.OrdemServicoRequestDTO;
import br.com.serratec.trabalhofinalapi.model.OrdemServico;
import br.com.serratec.trabalhofinalapi.service.OrdemServicoService;

@RestController
@RequestMapping("/os")
@Validated
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService ordemServicoService;

    @PostMapping
    public ResponseEntity<OrdemServico> inserir(@RequestBody OrdemServicoRequestDTO dto){
        return ResponseEntity.ok(ordemServicoService.inserir(dto));
    }

    @GetMapping("/disponibilidade")
    public ResponseEntity<Boolean> disponibilidade(@RequestParam String dataAgendamento) {
        return ResponseEntity.ok(ordemServicoService.estaDisponivel(dataAgendamento));
    }

}
