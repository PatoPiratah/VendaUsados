package com.example.demo.api.dto;

import com.example.demo.model.entity.Compra;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idVeiculo;

    private Long idPessoa;
    private Long idFuncionario;

    private Double valor;

    private LocalDateTime dataCompra;

    private String notaFiscal;

    public static CompraDTO create(Compra compra) {
        ModelMapper modelMapper = new ModelMapper();
        CompraDTO dto = modelMapper.map(compra, CompraDTO.class);
        
        return dto;

    }
}
