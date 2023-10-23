package com.teste.wayon.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

@SpringBootTest
public class TransferServiceTest {

    @Autowired
    private TransferService transferService;

    @Test
    public void testeCalcularTaxa() {
        LocalDate dataTransferencia = LocalDate.of(2023, 11, 30);
        LocalDate dataAgendada = LocalDate.of(2023, 10, 23);
        double valorTransferencia = 250.00;

        double taxa = transferService.calcularTaxa(dataTransferencia, valorTransferencia);

        Assertions.assertEquals(11.75, taxa);
    }
}
