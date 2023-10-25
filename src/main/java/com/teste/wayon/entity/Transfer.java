package com.teste.wayon.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("_id")
    private long id;

    @NotNull
    private long contaOrigem;

    @NotNull
    private long contaDestino;

    @Positive(message = "O valor da transferência deve ser maior que zero")
    @Column(nullable = false)
    private BigDecimal valorTransferencia;

    private BigDecimal taxa;

    @Column(nullable = false)
    private LocalDate dataTransferencia;

    @CreationTimestamp
    private LocalDate dataAgendada;

}
