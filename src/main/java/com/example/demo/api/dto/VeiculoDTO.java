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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private String chassi;
    private Long idTipoVeiculo;
    private String marca;
    private String modelo;

    private Long idEstoque;
    private Integer estoqueMax;

    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;

    public static VeiculoDTO create(Veiculo veiculo) {
        ModelMapper modelMapper = new ModelMapper();
        VeiculoDTO dto = modelMapper.map(veiculo, VeiculoDTO.class);

        return dto;
    }
}
