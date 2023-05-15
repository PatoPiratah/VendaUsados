package com.example.demo.api.dto;

import com.example.demo.model.entity.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idVeiculo;

    private Long idFuncionario;
    private Long idClientePF;
    private Long idClientePJ;

    private Double valor;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataVenda;

    private String notaFiscal;

    public static VendaDTO create(Venda compra) {
        ModelMapper modelMapper = new ModelMapper();
        VendaDTO dto = modelMapper.map(compra, VendaDTO.class);

        return dto;

    }
}

