package br.com.serratec.trabalhofinalapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.trabalhofinalapi.dto.PagamentoRequestDTO;
import br.com.serratec.trabalhofinalapi.model.Pagamento;
import br.com.serratec.trabalhofinalapi.service.PagamentoService;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @PostMapping
    public ResponseEntity<Pagamento> inserir(@RequestBody PagamentoRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.inserir(dto));
    }

    @GetMapping
    public ResponseEntity<List<Pagamento>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> buscar(@PathVariable Long id) {

        return ResponseEntity.ok(service.buscar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pagamento> alterar(
            @PathVariable Long id,
            @RequestBody PagamentoRequestDTO dto) {

        return ResponseEntity.ok(service.alterar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
