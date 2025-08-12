package com.romands.Controller;

import com.romands.Controller.Request.TransferenciaRequest;
import com.romands.Controller.Response.TransferenciaResponse;
import com.romands.Entity.Transferencia;
import com.romands.Entity.TransferenciaStatus;
import com.romands.Mapper.TransferenciaMapper;
import com.romands.Service.ProductService;
import com.romands.Service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transferencias")
public class TransferenciasController {

    @Autowired
    private TransferenciaService service;

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransferenciaRequest transferenciaRequest) {
        if (transferenciaRequest.transferenciaStatus() == TransferenciaStatus.FEITA) {
            transferenciaRequest.produtos().forEach(product ->
                    productService.diminuirQuantidade(product.getId(), product.getQuantidade())
            );
        }

        service.create(transferenciaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Transferência criada");
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        List<TransferenciaResponse> transferencias = service.findAll().stream().map(TransferenciaMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(transferencias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transferencia> detalhe(@PathVariable Long id) {
        Transferencia transferencia = service.findById(id);
        return ResponseEntity.ok(transferencia);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id, @RequestParam TransferenciaStatus status) {
        service.update(id, status);
        return ResponseEntity.ok("Status atualizado para " + status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelar(@PathVariable Long id) {
        service.update(id, TransferenciaStatus.CANCELADA);
        return ResponseEntity.ok("Transferência cancelada com sucesso");
    }
}
