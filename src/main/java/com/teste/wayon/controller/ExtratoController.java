package com.teste.wayon.controller;

import com.teste.wayon.entity.Transfer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teste.wayon.service.TransferService;

import java.util.List;

@RestController
public class ExtratoController {

    private final TransferService transferService;

    public ExtratoController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("/extrato/{numeroConta}")
    public ResponseEntity<List<Transfer>> obterExtratoPorConta(@PathVariable long numeroConta) {
        List<Transfer> extrato = transferService.obterExtratoPorConta(numeroConta);
        return ResponseEntity.ok(extrato);
    }
}
