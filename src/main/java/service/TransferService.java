package service;

import entity.Conta;
import entity.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ContaRepository;
import repository.TransferRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final ContaRepository contaRepository;

    @Autowired
    public TransferService(TransferRepository transferRepository, ContaRepository contaRepository) {
        this.transferRepository = transferRepository;
        this.contaRepository = contaRepository;
    }

    public void agendarTransferencia(Transfer transfer) {
        if (validarTransferencia(transfer)) {
            long contaOrigem = transfer.getContaOrigem().getConta();
            long contaDestino = transfer.getContaDestino().getConta();

            if (!contaRepository.existsByConta(contaOrigem)) {
                Conta novaContaOrigem = new Conta();
                novaContaOrigem.setConta(contaOrigem);
                novaContaOrigem.setSaldo(0.0);
                contaRepository.save(novaContaOrigem);
            }

            if (!contaRepository.existsByConta(contaDestino)) {
                Conta novaContaDestino = new Conta();
                novaContaDestino.setConta(contaDestino);
                novaContaDestino.setSaldo(0.0);
                contaRepository.save(novaContaDestino);
            }

            transfer.setDataAgendada(LocalDate.now());

            double taxa = calculartaxa(transfer.getDataTransferencia(), transfer.getValorTransferencia());
            transfer.setTaxa(taxa);

            transferRepository.save(transfer);
        } else {
            throw new IllegalArgumentException("Transferência inválida. Verifique os dados!");
        }
    }

    public List<Transfer> obterExtrato() {
        return transferRepository.findAll();
    }

    private double calculartaxa(LocalDate dataTransferencia, double valorTransferencia) {
        int diasTransferencia = carcularDiasDeTransferencia(dataTransferencia);

        if (diasTransferencia == 0) {
            return valorTransferencia * 0.025;
        } else if (diasTransferencia >= 1 && diasTransferencia <= 10) {
            return 0.00;
        } else if (diasTransferencia >= 11 && diasTransferencia <= 20) {
            return valorTransferencia * 0.082;
        } else if (diasTransferencia >= 21 && diasTransferencia <= 30) {
            return valorTransferencia * 0.069;
        } else if (diasTransferencia >= 31 && diasTransferencia <= 40) {
            return valorTransferencia * 0.047;
        } else if (diasTransferencia >= 41 && diasTransferencia <= 50) {
            return valorTransferencia * 0.017;
        } else {
            return 0.00;
        }
    }

    private int carcularDiasDeTransferencia(LocalDate dataTransferencia) {
        LocalDate dataAtual = LocalDate.now();
        return (int) dataAtual.until(dataTransferencia).getDays();
    }

    private boolean validarTransferencia(Transfer transfer) {
        if (transfer.getContaOrigem() == null || transfer.getContaDestino() == null) {
            return false;
        }

        if (transfer.getValorTransferencia() <= 0) {
            return false;
        }

        LocalDate dataAtual = LocalDate.now();
        if (transfer.getDataTransferencia().isBefore(dataAtual)) {
            return false;
        }

        return true;
    }
}
