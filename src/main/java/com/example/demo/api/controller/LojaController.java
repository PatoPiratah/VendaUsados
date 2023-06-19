package com.example.demo.api.controller;

import com.example.demo.api.dto.LojaDTO;
import com.example.demo.service.EnderecoService;
import com.example.demo.service.LojaService;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.Endereco;
import com.example.demo.model.entity.Loja;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/lojas")
@RequiredArgsConstructor


public class LojaController {
    private final LojaService service;
    private final EnderecoService enderecoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Loja> lojas = service.getLojas();
        return ResponseEntity.ok(lojas.stream().map(LojaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Loja> loja = service.getLojaById(id);
        if (!loja.isPresent()) {
            return new ResponseEntity("Loja não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(loja.map(LojaDTO::create));
    }

    @PostMapping()
    
    public ResponseEntity post(LojaDTO dto) {
        try {
            Loja loja = converter(dto);

            Endereco endereco = enderecoService.salvar(loja.getEndereco());
            loja.setEndereco(endereco);

            loja = service.salvar(loja);
            return new ResponseEntity(loja, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, LojaDTO dto) {
        if (!service.getLojaById(id).isPresent()) {
            return new ResponseEntity("Loja não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Loja loja = converter(dto);
            loja.setId(id);

            Endereco endereco = enderecoService.salvar(loja.getEndereco());
            loja.setEndereco(endereco);

            service.salvar(loja);
            return ResponseEntity.ok(loja);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Loja> loja = service.getLojaById(id);
        if (!loja.isPresent()) {
            return new ResponseEntity("Loja não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(loja.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Loja converter(LojaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Loja loja = modelMapper.map(dto, Loja.class);

        Endereco endereco = modelMapper.map(dto, Endereco.class);
        loja.setEndereco(endereco);

        return loja;
    }
}