package com.romands.Controller;

import com.romands.Controller.Request.TransferenciaRequest;
import com.romands.Controller.Response.TransferenciaResponse;
import com.romands.Entity.Transferencia;
import com.romands.Entity.TransferenciaStatus;
import com.romands.Mapper.TransferenciaMapper;
import com.romands.Service.ProductService;
import com.romands.Service.TransferenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Cria uma nova transferência")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transferência criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na transferência")
    })
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

    @Operation(summary = "Lista todas as transferências")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de transferências retornada")
    })
    @GetMapping
    public ResponseEntity<?> listar() {
        List<TransferenciaResponse> transferencias = service.findAll().stream()
                .map(TransferenciaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transferencias);
    }

    @Operation(summary = "Detalha uma transferência pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferência encontrada"),
            @ApiResponse(responseCode = "404", description = "Transferência não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> detalhe(@PathVariable Long id) {
        Transferencia transferencia = service.findById(id);
        TransferenciaResponse transferenciaResponse = TransferenciaMapper.toDTO(transferencia);
        return ResponseEntity.ok(transferenciaResponse);
    }

    @Operation(summary = "Atualiza o status de uma transferência")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transferência não encontrada")
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id, @RequestParam TransferenciaStatus status) {
        service.update(id, status);
        return ResponseEntity.ok("Status atualizado para " + status);
    }

    @Operation(summary = "Cancela uma transferência")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferência cancelada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transferência não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelar(@PathVariable Long id) {
        service.update(id, TransferenciaStatus.CANCELADA);
        return ResponseEntity.ok("Transferência cancelada com sucesso");
    }
}
