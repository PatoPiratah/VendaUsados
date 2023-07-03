package com.example.demo.service;

import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.ClientePJ;
import com.example.demo.model.repository.ClientePJRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientePJService {
        private ClientePJRepository repository;

        public ClientePJService(ClientePJRepository repository) {
            this.repository = repository;
        }

        public List<ClientePJ> getClientesPJ() {
            return repository.findAll();
        }

        public Optional<ClientePJ> getClientePJById(Long id) {
            return repository.findById(id);
        }

        @Transactional
        public ClientePJ salvar(ClientePJ clientePJ) {
            validar(clientePJ);
            return repository.save(clientePJ);
        }
        
        @Transactional
        public void excluir(ClientePJ clientePJ) {
            Objects.requireNonNull(clientePJ.getId());
            repository.delete(clientePJ);
        }
        
        public void validar(ClientePJ clientePJ) {
            
            if (clientePJ.getNome() == null || clientePJ.getNome().trim().equals("")) {
                throw new RegraNegocioException("Nome inválido");
            }

            if (clientePJ.getCnpj() == null || clientePJ.getCnpj().trim().equals("")) {
                throw new RegraNegocioException("CNPJ inválido");
            }

            if (clientePJ.getDataCriacao() == null) {
                throw new RegraNegocioException("Data inválida");
            }

            if (clientePJ.getNomeContato() == null || clientePJ.getNomeContato().trim().equals("")) {
                throw new RegraNegocioException("Nome do Contato inválido");
            }

            if (clientePJ.getTelefoneContato() == null || clientePJ.getTelefoneContato().trim().equals("")) {
                throw new RegraNegocioException("Telefone do Contato Invalido");
            }

            if (clientePJ.getEmail() == null || clientePJ.getEmail().trim().equals("")) {
                throw new RegraNegocioException("Email inválido");
            }

            if (clientePJ.getEndereco() == null || clientePJ.getEndereco().getId() == 0 ||
                    clientePJ.getEndereco().getId() == null) {
                throw new RegraNegocioException("Endereco inválido");
            }

            if (clientePJ.getCelular() == null || clientePJ.getCelular().trim().equals("")) {
                throw new RegraNegocioException("Celular inválido");
            }

        }
    }
