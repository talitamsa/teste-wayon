package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "contaOrigem")
    private Conta contaOrigem;
    @ManyToOne
    @JoinColumn(name = "contaDestino")
    private Conta contaDestino;
    private double valorTransferencia;
    private double taxa;
    private LocalDate dataTransferencia;
    private LocalDate dataAgendada;

}
