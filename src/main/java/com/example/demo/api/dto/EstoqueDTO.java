package com.example.demo.api.dto;

import com.example.demo.model.entity.Estoque;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EstoqueDTO {

    private Long id;

    private Integer estoqueMax;

    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;

    public static EstoqueDTO create(Estoque estoque) {
        ModelMapper modelMapper = new ModelMapper();
        EstoqueDTO dto = modelMapper.map(estoque, EstoqueDTO.class);

        dto.logradouro = estoque.getEndereco().getLogradouro();
        dto.complemento = estoque.getEndereco().getComplemento();
        dto.numero = estoque.getEndereco().getNumero();
        dto.cep = estoque.getEndereco().getCep();

        return dto;
    }
}

