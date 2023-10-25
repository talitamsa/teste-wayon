package com.teste.wayon.service;

import com.teste.wayon.config.Rate;
import com.teste.wayon.config.RateConfiguration;
import com.teste.wayon.entity.Transfer;
import com.teste.wayon.exceptions.DataTransferenciaInvalidaException;
import com.teste.wayon.exceptions.TaxaNaoEncontradaException;
import com.teste.wayon.exceptions.TransferenciaInvalidaException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.teste.wayon.repository.TransferRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TransferService {

    private final Validator validator;
    private final TransferRepository transferRepository;
    private final RateConfiguration rateConfiguration;

    @Autowired
    public TransferService(Validator validator, TransferRepository transferRepository, RateConfiguration rateConfiguration) {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.transferRepository = transferRepository;
        this.rateConfiguration = rateConfiguration;
    }

    public void agendarTransferencia(Transfer transfer) {
        // Método para agendar uma transferência.

        // Valida o objeto Transfer
        Set<ConstraintViolation<Transfer>> violations = validator.validate(transfer);
        if (!violations.isEmpty()) {
            throw new ValidationException("Erro de validação: " + violations);
        }

        if (validarTransferencia(transfer)) {
            long contaOrigem = transfer.getContaOrigem();
            long contaDestino = transfer.getContaDestino();
            LocalDate dataTransferencia = transfer.getDataTransferencia();

            BigDecimal taxa = calcularTaxa(dataTransferencia, transfer.getValorTransferencia());

            int diasTransferencia = LocalDate.now().until(dataTransferencia).getDays();

            if (diasTransferencia > 51) {
                throw new TaxaNaoEncontradaException("Transferência não permitida devido à falta de taxa aplicável.");
            }

            transfer.setContaOrigem(contaOrigem);
            transfer.setContaDestino(contaDestino);

            transfer.setTaxa(taxa);

            transferRepository.save(transfer);
        } else {
            throw new TransferenciaInvalidaException("Transferência inválida. Verifique os dados!");
        }
    }

    public List<Transfer> obterExtratoPorConta(long numeroConta) {
        // Método para obter o extrato de transferências por conta.
        return transferRepository.findByContaOrigem(numeroConta);
    }

    public BigDecimal calcularTaxa(LocalDate dataTransferencia, BigDecimal valorTransferencia) {
        LocalDate dataAtual = LocalDate.now();
        long diasTransferencia = dataTransferencia.toEpochDay() - dataAtual.toEpochDay();
        System.out.println("Dias de Transferência: " + diasTransferencia);

        if (diasTransferencia < 0) {
            throw new DataTransferenciaInvalidaException("Data de transferência inválida");
        } else {
            Optional<Rate> rate = rateConfiguration.getRateForDays((int) diasTransferencia);
            if (rate.isPresent()) {
                BigDecimal taxa = valorTransferencia.multiply(rate.get().getRate());
                System.out.println("Taxa Calculada: " + taxa);
                return taxa;
            } else {
                throw new TaxaNaoEncontradaException("Taxa de transferência não encontrada para os dias fornecidos.");
            }
        }
    }


    private boolean validarTransferencia(Transfer transfer) {
        // Método para validar a transferência.

        if (transfer == null || transfer.getValorTransferencia() == null || transfer.getDataTransferencia() == null) {
            return false;
        }

        if (transfer.getValorTransferencia().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        LocalDate dataAtual = LocalDate.now();
        if (transfer.getDataTransferencia().isBefore(dataAtual)) {
            return false;
        }

        return true;
    }

    public List<Transfer> obterTodosAgendamentos() {
        // Consulta todos os agendamentos (transferências) na base de dados
        return transferRepository.findAll();
    }
}
