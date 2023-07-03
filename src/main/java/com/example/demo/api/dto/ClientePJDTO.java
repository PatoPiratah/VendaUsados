package com.example.demo.api.dto;

import com.example.demo.model.entity.ClientePJ;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientePJDTO {
    private Long id;
    private String nome;
    private String email;
    private String telefone_fixo;
    private String celular;
    private String cnpj;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCriacao;
    private String nomeContato;
    private String telefoneContato;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;

    public static ClientePJDTO create(ClientePJ clientePJ) {
        ModelMapper modelMapper = new ModelMapper();
        ClientePJDTO dto = modelMapper.map(clientePJ, ClientePJDTO.class);

        dto.setLogradouro(clientePJ.getEndereco().getLogradouro());
        dto.setComplemento(clientePJ.getEndereco().getComplemento());
        dto.setNumero(clientePJ.getEndereco().getNumero());
        dto.setCep(clientePJ.getEndereco().getCep());
        return dto;
    }
}
