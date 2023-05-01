package com.example.demo.api.dto;

import com.example.demo.model.entity.Funcionario;
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

public class FuncionarioDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String nome;
    private String email;
    private String telefone_fixo;
    private String celular;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataAdmissao;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataDemissao;
    private String cargo;
    private String carteiraTrabalho;

    public static FuncionarioDTO create (Funcionario funcionario) {
        ModelMapper modelMapper = new ModelMapper();
        FuncionarioDTO dto = modelMapper.map(funcionario, FuncionarioDTO.class);

        dto.logradouro = funcionario.getEndereco().getLogradouro();
        dto.complemento = funcionario.getEndereco().getComplemento();
        dto.numero = funcionario.getEndereco().getNumero();
        dto.cep = funcionario.getEndereco().getCep();

        return dto;

    }
}