package com.example.demo.service;

import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.Venda;
import com.example.demo.model.repository.VendaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

        //Verificar se pessoa juridica e fisica são nulos ao mesmo tempo!

        if (venda.getClientePF() == null || venda.getClientePF().getId() == null ||
                venda.getClientePF().getId() == 0)
        {
            if(venda.getClientePJ() == null || venda.getClientePJ().getId() == null ||
                    venda.getClientePJ().getId() == 0)
            {
                throw new RegraNegocioException("Cliente inválido");
            }
        }

        // Verifica pessoa fisica e juridica ao mesmo tempo!

        if(venda.getClientePF() != null && venda.getClientePJ() != null)
        {
            throw new RegraNegocioException ("Vendendo para pessoa Juridica e Fisica ao mesmo tempo");
        }

        if (venda.getFuncionario() == null || venda.getFuncionario().getId() == null
                || venda.getFuncionario().getId() == 0) {
            throw new RegraNegocioException("Funcionario inválido");
        }

        if (venda.getValor() == null || (venda.getValor() <= 0)) {
            throw new RegraNegocioException("Valor inválido");
        }

        if (venda.getDataHoraVenda() == null) {
            throw new RegraNegocioException("Data de Venda inválida");
        }
        
        //TODO codificar o Caixa-Dois
        if (venda.getNotaFiscal() == null || venda.getNotaFiscal().trim().equals("")) {
            throw new RegraNegocioException("Nota Fiscal inválida");
        }
    }
}

