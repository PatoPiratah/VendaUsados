package com.example.demo.service;

import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.ClientePF;
import com.example.demo.model.repository.ClientePFRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientePFService {
        private ClientePFRepository repository;

        public ClientePFService(ClientePFRepository repository) {
            this.repository = repository;
        }

        public List<ClientePF> getClientesPF() {
            return repository.findAll();
        }

        public Optional<ClientePF> getClientePFById(Long id) {
            return repository.findById(id);
        }

        @Transactional
        public ClientePF salvar(ClientePF clientePF) {
            validar(clientePF);
            return repository.save(clientePF);
        }
        
        @Transactional
        public void excluir(ClientePF clientePF) {
            Objects.requireNonNull(clientePF.getId());
            repository.delete(clientePF);
        }
        
        public void validar(ClientePF clientePF) {
            
            if (clientePF.getNome() == null || clientePF.getNome().trim().equals("")) {
                throw new RegraNegocioException("Nome inválido");
            }

            if (clientePF.getCpf() == null || clientePF.getCpf().trim().equals("")) {
                throw new RegraNegocioException("CPF inválido");
            }

            if (clientePF.getDataNascimento() == null) {
                throw new RegraNegocioException("Data de Nascimento invalida");
            }

            if (clientePF.getEmail() == null || clientePF.getEmail().trim().equals("")) {
                throw new RegraNegocioException("Email inválido");
            }

            if (clientePF.getEndereco() == null || clientePF.getEndereco().getId() == 0 ||
                    clientePF.getEndereco().getId() == null) {
                throw new RegraNegocioException("Endereco inválido");
            }

            if (clientePF.getCelular() == null || clientePF.getCelular().trim().equals("")) {
                throw new RegraNegocioException("Celular inválido");
            }

        }
    }
