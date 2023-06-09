package com.example.demo.api.dto;

import com.example.demo.model.entity.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoDTO {

    private Long id;
    private String descricao;
    private String chassi;
    private Long idTipoVeiculo;
    private String marca;
    private String modelo;
    private String imagem;
    private Double valor;

    public static VeiculoDTO create(Veiculo veiculo) {
        ModelMapper modelMapper = new ModelMapper();
        VeiculoDTO dto = modelMapper.map(veiculo, VeiculoDTO.class);

        return dto;
    }
}
