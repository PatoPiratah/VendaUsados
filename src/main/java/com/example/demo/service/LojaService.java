package com.example.demo.service;

import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.*;
import com.example.demo.model.repository.LojaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LojaService {

    private LojaRepository repository;

    public LojaService(LojaRepository repository) {
        this.repository = repository;
    }

    public List<Loja> getLojas() {
        return repository.findAll();
    }

    public Optional<Loja> getLojaById(Long id) {
        return repository.findById(id);
    }


    @Transactional
    public Loja salvar(Loja loja) {
        validar(loja);
        return repository.save(loja);
    }

    @Transactional
    public void excluir(Loja loja) {
        Objects.requireNonNull(loja.getId());
        repository.delete(loja);
    }

    public void validar(Loja loja) {

        if (loja.getLojaMax() == null || loja.getLojaMax() <= 0) {
            throw new RegraNegocioException("Maximo de Loja inválido");
        }

        if (loja.getEndereco() == null || loja.getEndereco().getId() == null
                || loja.getEndereco().getId() == 0) {
            throw new RegraNegocioException("Endereco inválido");
        }
    }
}

