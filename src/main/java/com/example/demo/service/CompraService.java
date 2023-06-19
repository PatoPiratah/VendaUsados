package com.example.demo.service;

import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.Compra;
import com.example.demo.model.repository.CompraRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CompraService {
    private CompraRepository repository;

    public CompraService(CompraRepository repository) {
        this.repository = repository;
    }

    public List<Compra> getCompras() {
        return repository.findAll();
    }

    public Optional<Compra> getCompraById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Compra salvar(Compra compra) {
        validar(compra);
        return repository.save(compra);
    }

    @Transactional
    public void excluir(Compra compra) {
        Objects.requireNonNull(compra.getId());
        repository.delete(compra);
    }

    public void validar(Compra compra) {

        if (compra.getVeiculo() == null || compra.getVeiculo().getId() == null
                || compra.getVeiculo().getId() == 0) {
            throw new RegraNegocioException("Veiculo inválido");
        }

        if (compra.getValor() == null || (compra.getValor() <= 0)) {
            throw new RegraNegocioException("Valor inválido");
        }

        if (compra.getDataHoraCompra() == null) {
            throw new RegraNegocioException("Data de Compra inválida");
        }

        if (compra.getLoja() == null || compra.getLoja().getId() == null
                || compra.getLoja().getId() == 0) {
            throw new RegraNegocioException("Loja inválida");
        }
    }
}

