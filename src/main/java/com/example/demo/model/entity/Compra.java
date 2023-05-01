package com.example.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Veiculo veiculo;
    /*
    @ManyToOne
    private Pessoa pessoa;
    */

    private Double valor;

    private LocalDateTime dataCompra;

    private String notaFiscal;
}
