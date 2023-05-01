package com.example.demo.model.repository;

import com.example.demo.model.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

}
