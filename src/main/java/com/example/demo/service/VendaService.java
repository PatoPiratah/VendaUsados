package com.example.demo.service;

import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.Venda;
import com.example.demo.model.repository.VendaRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VendaService {
    private VendaRepository repository;

    public VendaService(VendaRepository repository) {
        this.repository = repository;
    }

    public List<Venda> getVendas() {
        return repository.findAll();
    }

    public Optional<Venda> getVendaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Venda salvar(Venda venda) {
        validar(venda);
        return repository.save(venda);
    }

    @Transactional
    public void excluir(Venda venda) {
        Objects.requireNonNull(venda.getId());
        repository.delete(venda);
    }

    public void validar(Venda venda) {

        if (venda.getVeiculo() == null || venda.getVeiculo().getId() == null
                || venda.getVeiculo().getId() == 0) {
            throw new RegraNegocioException("Veiculo inválido");
        }

        //TODO perguntar ao Marco Antonio
        /*
        if (venda.getPessoa() == null || venda.getPessoa().getId() == null
                || venda.getPessoa().getId() == 0) {
            throw new RegraNegocioException("Cliente inválido");
        }
        */

        if (venda.getFuncionario() == null || venda.getFuncionario().getId() == null
                || venda.getFuncionario().getId() == 0) {
            throw new RegraNegocioException("Funcionario inválido");
        }

        if (venda.getValor() == null || (venda.getValor() <= 0)) {
            throw new RegraNegocioException("Valor inválido");
        }

        if (venda.getDataVenda() == null) {
            throw new RegraNegocioException("Data de Venda inválida");
        }
        
        //TODO codificar o Caixa-Dois
        if (venda.getNotaFiscal() == null || venda.getNotaFiscal().trim().equals("")) {
            throw new RegraNegocioException("Nota Fiscal inválida");
        }
    }
}

