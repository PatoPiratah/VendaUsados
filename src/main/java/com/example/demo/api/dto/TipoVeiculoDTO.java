package com.example.demo.api.dto;

import com.example.demo.model.entity.TipoVeiculo;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoVeiculoDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipo;

    public static TipoVeiculoDTO create(TipoVeiculo tipo) {
        ModelMapper modelMapper = new ModelMapper();
        TipoVeiculoDTO dto = modelMapper.map(tipo, TipoVeiculoDTO.class);

        return dto;
    }
}
