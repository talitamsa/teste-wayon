package com.teste.wayon.controller;

import com.teste.wayon.entity.Transfer;
import com.teste.wayon.exceptions.TransferenciaInvalidaException;
import com.teste.wayon.repository.TransferRepository;
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

    private final TransferRepository transferRepository;

    @Autowired
    public TransferController(TransferService transferService, TransferRepository transferRepository) {
        this.transferService = transferService;
        this.transferRepository = transferRepository;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirTransferencia(@PathVariable Long id) {
        try {

            Transfer transferencia = transferRepository.findById(id)
                    .orElseThrow(() -> new TransferenciaInvalidaException("{\"message\": \"Transferência não encontrada!: \"}"));

            transferRepository.delete(transferencia);

            return ResponseEntity.ok("{\"message\": \"Transferência excluída com sucesso\"}");
        } catch (TransferenciaInvalidaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Erro ao excluir a transferência. \"}");
        }
    }

}
