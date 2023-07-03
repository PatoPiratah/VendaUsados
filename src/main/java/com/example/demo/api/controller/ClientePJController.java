package com.example.demo.api.controller;

import com.example.demo.api.dto.ClientePJDTO;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.ClientePJ;
import com.example.demo.model.entity.Endereco;
import com.example.demo.service.ClientePJService;
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
@RequestMapping("/api/v1/clientesPJ")
@RequiredArgsConstructor

public class ClientePJController {
    private final ClientePJService service;
    private final EnderecoService enderecoService;

    @GetMapping()
    public ResponseEntity<List<ClientePJDTO>> get() {
        List<ClientePJ> clientePJ = service.getClientesPJ();
        List<ClientePJDTO> clientePJDTOs = clientePJ.stream()
                .map(ClientePJDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientePJDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientePJDTO> get(@PathVariable("id") Long id) {
        Optional<ClientePJ> clientePJ = service.getClientePJById(id);
        if (!clientePJ.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ClientePJDTO.create(clientePJ.get()));
    }


    @PostMapping()
    public ResponseEntity<?> post(@RequestBody ClientePJDTO dto) {
        try {
            ClientePJ clientePJ = converter(dto);

            Endereco endereco = enderecoService.salvar(clientePJ.getEndereco());
            clientePJ.setEndereco(endereco);

            clientePJ = service.salvar(clientePJ);
            return new ResponseEntity<>(clientePJ, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody ClientePJDTO dto) {
        if (!service.getClientePJById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        try {
            ClientePJ clientePJ = converter(dto);
            clientePJ.setId(id);

            Endereco endereco = enderecoService.salvar(clientePJ.getEndereco());
            clientePJ.setEndereco(endereco);

            service.salvar(clientePJ);
            return ResponseEntity.ok(clientePJ);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ClientePJ> clientePJ = service.getClientePJById(id);
        if (!clientePJ.isPresent()) {
            return new ResponseEntity("Cliente PJ não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(clientePJ.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ClientePJ converter(ClientePJDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ClientePJ clientePJ = modelMapper.map(dto, ClientePJ.class);

        Endereco endereco = modelMapper.map(dto, Endereco.class);
        clientePJ.setEndereco(endereco);

        return clientePJ;
    }
}