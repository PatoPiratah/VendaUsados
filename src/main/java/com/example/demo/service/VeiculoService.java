package com.example.demo.service;

import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.Veiculo;
import com.example.demo.model.repository.VeiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VeiculoService {

    private VeiculoRepository repository;

    public VeiculoService(VeiculoRepository repository) {
        this.repository = repository;
    }

    public List<Veiculo> getVeiculos() {
        return repository.findAll();
    }

    public Optional<Veiculo> getVeiculoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Veiculo salvar(Veiculo veiculo) {
        validar(veiculo);
        return repository.save(veiculo);
    }

    @Transactional
    public void excluir(Veiculo veiculo) {
        Objects.requireNonNull(veiculo.getId());
        repository.delete(veiculo);
    }

    public void validar(Veiculo veiculo) {

        if (veiculo.getDescricao() == null || veiculo.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Descrição inválida");
        }

        if (veiculo.getTipoVeiculo() == null || veiculo.getTipoVeiculo().getId() == 0 ||
                veiculo.getTipoVeiculo().getId() == null) {
            throw new RegraNegocioException("Tipo Veiculo inválido");
        }

        if (veiculo.getEstoque() == null || veiculo.getEstoque().getId() == 0 ||
                veiculo.getEstoque().getId() == null) {
            throw new RegraNegocioException("Estoque inválido");
        }

        if (veiculo.getMarca() == null || veiculo.getMarca().trim().equals("")) {
            throw new RegraNegocioException("Marca inválida");
        }

        if (veiculo.getModelo() == null || veiculo.getModelo().trim().equals("")) {
            throw new RegraNegocioException("Modelo inválido");
        }
    }
}