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
    public ResponseEntity<List<TipoVeiculoDTO>> get() {
        List<TipoVeiculo> tiposVeiculo = service.getTiposVeiculo();
        List<TipoVeiculoDTO> tipoVeiculoDTOs = tiposVeiculo.stream()
                .map(TipoVeiculoDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tipoVeiculoDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoVeiculoDTO> get(@PathVariable("id") Long id) {
        Optional<TipoVeiculo> tipoVeiculo = service.getTipoVeiculoById(id);
        if (!tipoVeiculo.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(TipoVeiculoDTO.create(tipoVeiculo.get()));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody TipoVeiculoDTO dto) {
        try {
            TipoVeiculo tipoVeiculo = converter(dto);
            tipoVeiculo = service.salvar(tipoVeiculo);
            return new ResponseEntity<>(tipoVeiculo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody TipoVeiculoDTO dto) {
        if (!service.getTipoVeiculoById(id).isPresent()) {
            return ResponseEntity.notFound().build();
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
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<TipoVeiculo> tipoVeiculo = service.getTipoVeiculoById(id);
        if (!tipoVeiculo.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        try {
            service.excluir(tipoVeiculo.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
