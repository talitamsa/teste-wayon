package com.teste.wayon.service;

import com.teste.wayon.entity.Conta;
import com.teste.wayon.entity.Transfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.teste.wayon.repository.ContaRepository;
import com.teste.wayon.repository.TransferRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransferService {

    private static final Logger logger = LoggerFactory.getLogger(TransferService.class);
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

            logger.info("Data de transferência antes da configuração: {}", transfer.getDataTransferencia());

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

            LocalDate dataTransferencia = transfer.getDataTransferencia();

            logger.info("Data de transferência após a configuração: {}", dataTransferencia);

            double taxa = calcularTaxa(dataTransferencia, transfer.getValorTransferencia());
            if (taxa == -1) {
                throw new IllegalArgumentException("Transferência não permitida devido à falta de taxa aplicável.");
            }

            logger.info("Taxa calculada: {}", taxa);

            transfer.setTaxa(taxa);

            transferRepository.save(transfer);
        } else {
            throw new IllegalArgumentException("Transferência inválida. Verifique os dados!");
        }
    }

    public List<Transfer> obterExtratoPorConta(long numeroConta) {
        return transferRepository.findByContaOrigemContaOrContaDestinoConta(numeroConta, numeroConta);
    }

    public double calcularTaxa(LocalDate dataTransferencia, double valorTransferencia) {
        LocalDate dataAgendamento = LocalDate.now();
        int diasTransferencia = (int) dataAgendamento.until(dataTransferencia).getDays();

        if (diasTransferencia < 0) {
            throw new IllegalArgumentException("Data de transferência inválida");
        } else if (diasTransferencia == 0) {
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
            throw new IllegalArgumentException("Transferência não permitida devido à falta de taxa aplicável.");
        }
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
