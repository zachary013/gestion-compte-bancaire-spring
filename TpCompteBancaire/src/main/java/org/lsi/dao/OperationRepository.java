package org.lsi.dao;

import org.lsi.entities.Compte;
import org.lsi.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findByCompte(Compte compte);

    Page<Operation> findByCompteCodeCompte(String codeCompte, Pageable pageable);

    @Query("SELECT COUNT(o) FROM Operation o")
    Long countTotalOperations(); // Custom query to count total operations

}