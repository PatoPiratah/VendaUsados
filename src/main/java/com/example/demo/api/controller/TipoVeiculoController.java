package com.example.demo.api.controller;

import com.example.demo.api.dto.TipoVeiculoDTO;
import com.example.demo.service.TipoVeiculoService;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.TipoVeiculo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tiposVeiculo")
@RequiredArgsConstructor


public class TipoVeiculoController {
    private final TipoVeiculoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<TipoVeiculo> tiposVeiculo = service.getTiposVeiculo();
        return ResponseEntity.ok(tiposVeiculo.stream().map(TipoVeiculoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TipoVeiculo> tipoVeiculo = service.getTipoVeiculoById(id);
        if (!tipoVeiculo.isPresent()) {
            return new ResponseEntity("Tipo de Veiculo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoVeiculo.map(TipoVeiculoDTO::create));
    }

    @PostMapping()
    
    public ResponseEntity post(TipoVeiculoDTO dto) {
        try {
            TipoVeiculo tipoVeiculo = converter(dto);
            tipoVeiculo = service.salvar(tipoVeiculo);
            return new ResponseEntity(tipoVeiculo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, TipoVeiculoDTO dto) {
        if (!service.getTipoVeiculoById(id).isPresent()) {
            return new ResponseEntity("Tipo de Veiculo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            TipoVeiculo tipoVeiculo = converter(dto);
            tipoVeiculo.setId(id);
            service.salvar(tipoVeiculo);
            return ResponseEntity.ok(tipoVeiculo);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<TipoVeiculo> tipoVeiculo = service.getTipoVeiculoById(id);
        if (!tipoVeiculo.isPresent()) {
            return new ResponseEntity("Tipo Veiculo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tipoVeiculo.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public TipoVeiculo converter(TipoVeiculoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        TipoVeiculo tipoVeiculo = modelMapper.map(dto, TipoVeiculo.class);

        return tipoVeiculo;
    }
}