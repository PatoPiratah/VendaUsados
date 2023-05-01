package com.example.demo.api.controller;

import com.example.demo.api.dto.EstoqueDTO;
import com.example.demo.service.EnderecoService;
import com.example.demo.service.EstoqueService;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.Endereco;
import com.example.demo.model.entity.Estoque;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/estoques")
@RequiredArgsConstructor


public class EstoqueController {
    private final EstoqueService service;
    private final EnderecoService enderecoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Estoque> estoques = service.getEstoques();
        return ResponseEntity.ok(estoques.stream().map(EstoqueDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Estoque> estoque = service.getEstoqueById(id);
        if (!estoque.isPresent()) {
            return new ResponseEntity("Estoque não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(estoque.map(EstoqueDTO::create));
    }

    @PostMapping()
    
    public ResponseEntity post(EstoqueDTO dto) {
        try {
            Estoque estoque = converter(dto);
            estoque = service.salvar(estoque);

            Endereco endereco = enderecoService.salvar(estoque.getEndereco());
            estoque.setEndereco(endereco);

            return new ResponseEntity(estoque, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, EstoqueDTO dto) {
        if (!service.getEstoqueById(id).isPresent()) {
            return new ResponseEntity("Estoque não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Estoque estoque = converter(dto);
            estoque.setId(id);

            Endereco endereco = enderecoService.salvar(estoque.getEndereco());
            estoque.setEndereco(endereco);

            service.salvar(estoque);
            return ResponseEntity.ok(estoque);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Estoque> estoque = service.getEstoqueById(id);
        if (!estoque.isPresent()) {
            return new ResponseEntity("Estoque não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(estoque.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Estoque converter(EstoqueDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Estoque estoque = modelMapper.map(dto, Estoque.class);

        Endereco endereco = modelMapper.map(dto, Endereco.class);
        estoque.setEndereco(endereco);

        return estoque;
    }
}