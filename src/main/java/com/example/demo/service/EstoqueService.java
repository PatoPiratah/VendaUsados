package com.example.demo.service;

import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.*;
import com.example.demo.model.repository.EstoqueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EstoqueService {

    private EstoqueRepository repository;

    public EstoqueService(EstoqueRepository repository) {
        this.repository = repository;
    }

    public List<Estoque> getEstoques() {
        return repository.findAll();
    }

    public Optional<Estoque> getEstoqueById(Long id) {
        return repository.findById(id);
    }


    @Transactional
    public Estoque salvar(Estoque estoque) {
        validar(estoque);
        return repository.save(estoque);
    }

    @Transactional
    public void excluir(Estoque estoque) {
        Objects.requireNonNull(estoque.getId());
        repository.delete(estoque);
    }

    public void validar(Estoque estoque) {

        if (estoque.getEstoqueMax() == null || estoque.getEstoqueMax() <= 0) {
            throw new RegraNegocioException("Maximo de Estoque inválido");
        }

        if (estoque.getEndereco() == null || estoque.getEndereco().getId() == null
                || estoque.getEndereco().getId() == 0) {
            throw new RegraNegocioException("Endereco inválido");
        }
    }
}

