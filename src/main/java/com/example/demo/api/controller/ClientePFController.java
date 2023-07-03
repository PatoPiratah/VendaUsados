package com.example.demo.api.controller;

import com.example.demo.api.dto.ClientePFDTO;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.ClientePF;
import com.example.demo.model.entity.Endereco;
import com.example.demo.service.ClientePFService;
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
@RequestMapping("/api/v1/clientesPF")
@RequiredArgsConstructor

public class ClientePFController {
    private final ClientePFService service;
    private final EnderecoService enderecoService;

    @GetMapping()
    public ResponseEntity<List<ClientePFDTO>> get() {
        List<ClientePF> clientePFs = service.getClientesPF();
        List<ClientePFDTO> clientePFDTOs = clientePFs.stream()
                .map(ClientePFDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientePFDTOs);
    }


    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ClientePF> clientePF = service.getClientePFById(id);
        if (!clientePF.isPresent()) {
            return new ResponseEntity("Cliente PF não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(clientePF.map(ClientePFDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody ClientePFDTO dto) {
        try {
            ClientePF clientePF = converter(dto);

            Endereco endereco = enderecoService.salvar(clientePF.getEndereco());
            clientePF.setEndereco(endereco);

            clientePF = service.salvar(clientePF);
            return new ResponseEntity(clientePF, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, ClientePFDTO dto) {
        if (!service.getClientePFById(id).isPresent()) {
            return new ResponseEntity("Cliente PF não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            ClientePF clientePF = converter(dto);
            clientePF.setId(id);

            Endereco endereco = enderecoService.salvar(clientePF.getEndereco());
            clientePF.setEndereco(endereco);

            service.salvar(clientePF);
            return ResponseEntity.ok(clientePF);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ClientePF> clientePF = service.getClientePFById(id);
        if (!clientePF.isPresent()) {
            return new ResponseEntity("Cliente PF não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(clientePF.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ClientePF converter(ClientePFDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ClientePF clientePF = modelMapper.map(dto, ClientePF.class);

        Endereco endereco = modelMapper.map(dto, Endereco.class);
        clientePF.setEndereco(endereco);

        return clientePF;
    }
}