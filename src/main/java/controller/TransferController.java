package controller;

import entity.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.TransferService;

@RestController
@RequestMapping("/transferencia")
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/agendar")
    public ResponseEntity<String> agendarTransferencia(@RequestBody Transfer transfer) {
        try {
            transferService.agendarTransferencia(transfer);
            return ResponseEntity.ok("Transferência agendada com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
