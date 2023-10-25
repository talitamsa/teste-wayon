package com.teste.wayon.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
public class TransferServiceTest {

    @Autowired
    private TransferService transferService;

    @Test
    public void testeCalcularTaxa() {
        LocalDate dataTransferencia = LocalDate.of(2023, 11, 29);
        BigDecimal valorTransferencia = new BigDecimal("275");

        BigDecimal taxa = transferService.calcularTaxa(dataTransferencia, valorTransferencia);

        BigDecimal expectedTaxa = new BigDecimal("12.925");

        Assertions.assertEquals(expectedTaxa, taxa);
    }
}
