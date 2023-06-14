package com.example.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Loja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer lojaMax;
    @ManyToOne
    private Endereco endereco;

    @ManyToMany
    @JoinTable(name = "loja_veiculo",
            joinColumns = @JoinColumn(name = "loja_id"),
            inverseJoinColumns = @JoinColumn(name = "veiculo_id"))
    private List<Veiculo> veiculos = new ArrayList<Veiculo>();

}
