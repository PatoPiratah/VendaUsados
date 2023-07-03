package com.example.demo.api.controller;

import com.example.demo.api.dto.CompraDTO;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.Compra;
import com.example.demo.model.entity.Loja;
import com.example.demo.model.entity.Veiculo;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/compras")
@RequiredArgsConstructor

public class CompraController {
    private final com.example.demo.service.CompraService service;
    private final VeiculoService veiculoService;
    private final LojaService lojaService;

    @GetMapping()
    public ResponseEntity<List<CompraDTO>> get() {
        List<Compra> compra = service.getCompras();
        List<CompraDTO> compraDTOs = compra.stream()
                .map(CompraDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(compraDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraDTO> get(@PathVariable("id") Long id) {
        Optional<Compra> compra = service.getCompraById(id);
        if (!compra.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(CompraDTO.create(compra.get()));
    }


    @PostMapping()
    public ResponseEntity<?> post(@RequestBody CompraDTO dto) {
        try {
            Compra compra = converter(dto);
            compra = service.salvar(compra);
            return new ResponseEntity<>(compra, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody CompraDTO dto) {
        if (!service.getCompraById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        try {
            Compra compra = converter(dto);
            compra.setId(id);
            service.salvar(compra);
            return ResponseEntity.ok(compra);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Compra> compra = service.getCompraById(id);
        if (!compra.isPresent()) {
            return new ResponseEntity("Compra não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(compra.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Compra converter(CompraDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Compra compra = modelMapper.map(dto, Compra.class);

        if (dto.getIdVeiculo() != null) {
            Optional<Veiculo> veiculo = veiculoService.getVeiculoById(dto.getIdVeiculo());
            if (!veiculo.isPresent()) {
                compra.setVeiculo(null);
            } else {
                compra.setVeiculo(veiculo.get());
                if (dto.getIdLoja() != null) {
                    Optional<Loja> loja = lojaService.getLojaById(dto.getIdLoja());
                    if(loja.isPresent()) {
                        // Chama a lista, adiciona um veiculo no loja e atualiza a variavel no banco de dados!
                        Loja lojaSalvar = loja.get();
                        List<Veiculo> veiculosSalvar = lojaSalvar.getVeiculos();
                        veiculosSalvar.add(veiculo.get());
                        lojaSalvar.setVeiculos(veiculosSalvar);
                        lojaService.salvar(lojaSalvar);
                        compra.setLoja(lojaSalvar);
                    } else {
                        compra.setLoja(null);
                    }
                }
            }
        }
        return compra;
    }
}
