package com.example.demo.api.dto;

import com.example.demo.model.entity.ClientePJ;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ClientePJDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone_fixo;
    private String celular;
    private String cnpj;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

        dto.logradouro = clientePJ.getEndereco().getLogradouro();
        dto.complemento = clientePJ.getEndereco().getComplemento();
        dto.numero = clientePJ.getEndereco().getNumero();
        dto.cep = clientePJ.getEndereco().getCep();
        return dto;
    }
}