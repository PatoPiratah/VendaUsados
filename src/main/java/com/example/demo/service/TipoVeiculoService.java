package com.example.demo.service;

import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.TipoVeiculo;
import com.example.demo.model.repository.TipoVeiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TipoVeiculoService {

    private TipoVeiculoRepository repository;

    public TipoVeiculoService(TipoVeiculoRepository repository) {
        this.repository = repository;
    }

    public List<TipoVeiculo> getTiposVeiculo() {
        return repository.findAll();
    }

    public Optional<TipoVeiculo> getTipoVeiculoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public TipoVeiculo salvar(TipoVeiculo tipoVeiculo) {
        validar(tipoVeiculo);
        return repository.save(tipoVeiculo);
    }

    @Transactional
    public void excluir(TipoVeiculo tipoVeiculo) {
        Objects.requireNonNull(tipoVeiculo.getId());
        repository.delete(tipoVeiculo);
    }

    public void validar(TipoVeiculo tipoVeiculo) {

        if (tipoVeiculo.getTipo() == null || tipoVeiculo.getTipo().trim().equals("")) {
            throw new RegraNegocioException("Tipo de veiculo inválido");
        }
    }
}
