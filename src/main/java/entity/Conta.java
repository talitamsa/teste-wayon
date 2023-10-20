package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long conta;
    private double saldo;

    @OneToMany(mappedBy = "contaOrigem")
    private List<Transfer> transferenciasEnviadas;

    @OneToMany(mappedBy = "contaDestino")
    private List<Transfer> transferenciasRecebidas;

}
