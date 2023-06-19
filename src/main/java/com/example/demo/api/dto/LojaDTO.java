package com.example.demo.api.dto;

import com.example.demo.model.entity.Loja;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LojaDTO {

    private Long id;

    private Integer lojaMax;

    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;

    public static LojaDTO create(Loja loja) {
        ModelMapper modelMapper = new ModelMapper();
        LojaDTO dto = modelMapper.map(loja, LojaDTO.class);

        dto.logradouro = loja.getEndereco().getLogradouro();
        dto.complemento = loja.getEndereco().getComplemento();
        dto.numero = loja.getEndereco().getNumero();
        dto.cep = loja.getEndereco().getCep();

        return dto;
    }
}

