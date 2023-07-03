package com.example.demo.api.controller;

import com.example.demo.api.dto.VeiculoDTO;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.TipoVeiculo;
import com.example.demo.model.entity.Veiculo;
import com.example.demo.service.TipoVeiculoService;
import com.example.demo.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.List;
import java.net.URI;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/veiculos")
@RequiredArgsConstructor
@Validated
public class VeiculoController {

    private final VeiculoService service;
    private final TipoVeiculoService tipoVeiculoService;

    @GetMapping()
    public ResponseEntity<List<VeiculoDTO>> get() {
        List<Veiculo> veiculos = service.getVeiculos();
        List<VeiculoDTO> veiculoDTOs = veiculos.stream()
                .map(VeiculoDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(veiculoDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDTO> get(@PathVariable("id") Long id) {
        Optional<Veiculo> veiculo = service.getVeiculoById(id);
        if (!veiculo.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(VeiculoDTO.create(veiculo.get()));
    }

    @PostMapping()
    public ResponseEntity<Veiculo> post(@RequestBody @Valid VeiculoDTO dto) {
        try {
            Veiculo veiculo = converter(dto);
            veiculo = service.salvar(veiculo);
            return ResponseEntity.created(URI.create("/api/v1/veiculos/" + veiculo.getId())).body(veiculo);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> atualizar(@PathVariable("id") Long id, @RequestBody @Valid VeiculoDTO dto) {
        if (!service.getVeiculoById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        try {
            Veiculo veiculo = converter(dto);
            veiculo.setId(id);
            service.salvar(veiculo);
            return ResponseEntity.ok(veiculo);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        Optional<Veiculo> veiculo = service.getVeiculoById(id);
        if (!veiculo.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        try {
            service.excluir(veiculo.get());
            return ResponseEntity.noContent().build();
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public Veiculo converter(VeiculoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Veiculo veiculo = modelMapper.map(dto, Veiculo.class);

        if (dto.getIdTipoVeiculo() != null) {
            Optional<TipoVeiculo> tipoVeiculo = tipoVeiculoService.getTipoVeiculoById(dto.getIdTipoVeiculo());
            if (!tipoVeiculo.isPresent()) {
                veiculo.setTipoVeiculo(null);
            } else {
                veiculo.setTipoVeiculo(tipoVeiculo.get());
            }
        }
        return veiculo;
    }
}
