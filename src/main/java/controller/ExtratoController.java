package controller;

import entity.Transfer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.TransferService;

import java.util.List;

@RestController
@RequestMapping("/extrato")
public class ExtratoController {

    private final TransferService transferService;

    public ExtratoController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping
    public ResponseEntity<List<Transfer>> obterExtrato() {
        List<Transfer> extrato = transferService.obterExtrato();
        return ResponseEntity.ok(extrato);
    }
}
