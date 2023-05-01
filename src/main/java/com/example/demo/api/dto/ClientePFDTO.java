package com.example.demo.api.dto;

import com.example.demo.model.entity.ClientePF;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ClientePFDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String CPF;
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
    
    dto.logradouro = clientePF.getEndereco().getLogradouro();
    dto.complemento = clientePF.getEndereco().getComplemento();
    dto.numero = clientePF.getEndereco().getNumero();
    dto.cep = clientePF.getEndereco().getCep();
    return dto;

    }
}
