package org.lsi.dao;

import org.lsi.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findByCompteCodeCompte(String codeCompte);

    @Query("SELECT o FROM Operation o WHERE o.compte.codeCompte = :x ORDER BY o.dateOperation DESC")
    Page<Operation> listOperation(@Param("x") String codeCompte, Pageable pageable);
}