package com.teste.wayon.repository;

import com.teste.wayon.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    boolean existsByConta(long conta);
}
