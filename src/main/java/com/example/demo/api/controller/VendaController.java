package com.example.demo.api.controller;

import com.example.demo.api.dto.VendaDTO;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.*;
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
@RequestMapping("/api/v1/vendas")
@RequiredArgsConstructor

public class VendaController {
    private final VendaService service;
    private final FuncionarioService funcionarioService;
    private final ClientePFService clientePFService;
    private final ClientePJService clientePJService;

    private final VeiculoService veiculoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Venda> venda = service.getVendas();
        return ResponseEntity.ok(venda.stream().map(VendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Venda> venda = service.getVendaById(id);
        if (!venda.isPresent()) {
            return new ResponseEntity("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(venda.map(VendaDTO::create));
    }

    @PostMapping()

    public ResponseEntity post(VendaDTO dto) {
        try {
            Venda venda = converter(dto);

            venda = service.salvar(venda);
            return new ResponseEntity(venda, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, VendaDTO dto) {
        if (!service.getVendaById(id).isPresent()) {
            return new ResponseEntity("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Venda venda = converter(dto);
            venda.setId(id);

            service.salvar(venda);
            return ResponseEntity.ok(venda);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Venda> venda = service.getVendaById(id);
        if (!venda.isPresent()) {
            return new ResponseEntity("Venda não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(venda.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Venda converter(VendaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Venda venda = modelMapper.map(dto, Venda.class);

        if (dto.getIdFuncionario() != null) {
            Optional<Funcionario> funcionario = funcionarioService.getFuncionarioById(dto.getIdFuncionario());
            if (!funcionario.isPresent()) {
                venda.setFuncionario(null);
            } else {
                venda.setFuncionario(funcionario.get());
            }
        }

        if (dto.getIdVeiculo() != null) {
            Optional<Veiculo> veiculo = veiculoService.getVeiculoById(dto.getIdVeiculo());
            if (!veiculo.isPresent()) {
                venda.setVeiculo(null);
            } else {
                venda.setVeiculo(veiculo.get());
            }
        }
        if (dto.getIdClientePF() != null) {
            Optional<ClientePF> clientePF = clientePFService.getClientePFById(dto.getIdClientePF());
            if (!clientePF.isPresent()) {
                venda.setClientePF(null);
            } else {
                venda.setClientePF(clientePF.get());
            }
        }
        if (dto.getIdClientePJ() != null) {
            Optional<ClientePJ> clientePJ = clientePJService.getClientePJById(dto.getIdClientePJ());
            if (!clientePJ.isPresent()) {
                venda.setClientePJ(null);
            } else {
                venda.setClientePJ(clientePJ.get());
            }
        }
        return venda;
    }
}
