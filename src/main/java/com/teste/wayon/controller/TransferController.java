package com.teste.wayon.controller;

import com.teste.wayon.entity.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.teste.wayon.service.TransferService;

import java.util.List;

@RestController
@RequestMapping("/transferencias")
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> agendarTransferencia(@RequestBody Transfer transfer) {
        try {
            // Método para agendar uma transferência.
            transferService.agendarTransferencia(transfer);
            return ResponseEntity.ok("{\"message\": \"Transferência agendada com sucesso.\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/{numeroConta}")
    public ResponseEntity<List<Transfer>> obterExtratoPorConta(@PathVariable long numeroConta) {
        // Método para obter o extrato de transferências de uma conta específica.
        List<Transfer> extrato = transferService.obterExtratoPorConta(numeroConta);
        return ResponseEntity.ok(extrato);
    }

    @GetMapping
    public ResponseEntity<List<Transfer>> obterTodosAgendamentos() {
        // Método para obter todos os agendamentos (transferências) na base de dados.
        List<Transfer> extrato = transferService.obterTodosAgendamentos();
        return ResponseEntity.ok(extrato);
    }
}
