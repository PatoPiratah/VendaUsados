package com.example.demo.api.dto;

import com.example.demo.model.entity.ClientePF;
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
public class ClientePFDTO {
    private Long id;
    private String nome;
    private String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    private String telefone_fixo;
    private String celular;
    private String email;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;

    public static ClientePFDTO create(ClientePF clientePF) {
        ModelMapper modelMapper = new ModelMapper();
        ClientePFDTO dto = modelMapper.map(clientePF, ClientePFDTO.class);

        dto.setLogradouro(clientePF.getEndereco().getLogradouro());
        dto.setComplemento(clientePF.getEndereco().getComplemento());
        dto.setNumero(clientePF.getEndereco().getNumero());
        dto.setCep(clientePF.getEndereco().getCep());
        return dto;
    }
}
