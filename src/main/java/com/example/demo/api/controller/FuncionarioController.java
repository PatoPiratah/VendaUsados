package com.example.demo.api.controller;

import com.example.demo.api.dto.FuncionarioDTO;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.Funcionario;
import com.example.demo.model.entity.Endereco;
import com.example.demo.service.FuncionarioService;
import com.example.demo.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/funcionarios")
@RequiredArgsConstructor

public class FuncionarioController {
    private final FuncionarioService service;
    private final EnderecoService enderecoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Funcionario> funcionario = service.getFuncionarios();
        return ResponseEntity.ok(funcionario.stream().map(FuncionarioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = service.getFuncionarioById(id);
        if (!funcionario.isPresent()) {
            return new ResponseEntity("Funcionario não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(funcionario.map(FuncionarioDTO::create));
    }

    @PostMapping()

    public ResponseEntity post(FuncionarioDTO dto) {
        try {
            Funcionario funcionario = converter(dto);

            Endereco endereco = enderecoService.salvar(funcionario.getEndereco());
            funcionario.setEndereco(endereco);

            funcionario = service.salvar(funcionario);
            return new ResponseEntity(funcionario, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, FuncionarioDTO dto) {
        if (!service.getFuncionarioById(id).isPresent()) {
            return new ResponseEntity("Funcionario não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Funcionario funcionario = converter(dto);
            funcionario.setId(id);

            Endereco endereco = enderecoService.salvar(funcionario.getEndereco());
            funcionario.setEndereco(endereco);

            service.salvar(funcionario);
            return ResponseEntity.ok(funcionario);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = service.getFuncionarioById(id);
        if (!funcionario.isPresent()) {
            return new ResponseEntity("Funcionario não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(funcionario.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Funcionario converter(FuncionarioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Funcionario funcionario = modelMapper.map(dto, Funcionario.class);

        Endereco endereco = modelMapper.map(dto, Endereco.class);
        funcionario.setEndereco(endereco);

        return funcionario;
    }
}
