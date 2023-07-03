package com.example.demo.api.dto;

import com.example.demo.model.entity.Compra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraDTO {
    private Long id;
    private Long idVeiculo;
    private Double valor;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataHoraCompra;

    private String notaFiscal;
    private Long idLoja;

    public static CompraDTO create(Compra compra) {
        CompraDTO dto = new CompraDTO();
        dto.setId(compra.getId());
        dto.setIdVeiculo(compra.getVeiculo().getId());
        dto.setValor(compra.getValor());
        dto.setDataHoraCompra(compra.getDataHoraCompra());
        dto.setNotaFiscal(compra.getNotaFiscal());
        dto.setIdLoja(compra.getLoja().getId());
        return dto;
    }
}
