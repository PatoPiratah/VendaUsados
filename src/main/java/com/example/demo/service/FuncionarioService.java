package com.example.demo.service;

import com.example.demo.exception.RegraNegocioException;
import com.example.demo.model.entity.*;
import com.example.demo.model.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FuncionarioService {

        private FuncionarioRepository repository;

        public FuncionarioService(FuncionarioRepository repository) {
            this.repository = repository;
        }

        public List<Funcionario> getFuncionarios() {
            return repository.findAll();
        }

        public Optional<Funcionario> getFuncionarioById(Long id) {
            return repository.findById(id);
        }


        @Transactional
        public Funcionario salvar(Funcionario funcionario) {
            validar(funcionario);
            return repository.save(funcionario);
        }

        @Transactional
        public void excluir(Funcionario funcionario) {
            Objects.requireNonNull(funcionario.getId());
            repository.delete(funcionario);
        }

        public void validar(Funcionario funcionario) {

            if (funcionario.getNome() == null || funcionario.getNome().trim().equals("")) {
                throw new RegraNegocioException("Nome inválido");
            }

            if (funcionario.getEmail() == null || funcionario.getEmail().trim().equals("")) {
                throw new RegraNegocioException("Email inválido");
            }

            if (funcionario.getEndereco() == null || funcionario.getEndereco().getId() == 0 ||
                    funcionario.getEndereco().getId() == null) {
                throw new RegraNegocioException("Endereco inválido");
            }

            if ((funcionario.getDataAdmissao() == null)) {
                throw new RegraNegocioException("Data de Admissao inválida");
            }

            if (funcionario.getCargo() == null || funcionario.getCargo().trim().equals("")) {
                throw new RegraNegocioException("Cargo inválido");
            }

            if (funcionario.getCarteiraTrabalho() == null || funcionario.getCarteiraTrabalho().trim().equals("")) {
                throw new RegraNegocioException("Carteira inválida");
            }

            if (funcionario.getCelular() == null || funcionario.getCelular().trim().equals("")) {
                throw new RegraNegocioException("Celular inválido");
            }
        }
}
