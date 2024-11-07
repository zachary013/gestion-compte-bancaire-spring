package org.lsi.dao;

import org.lsi.entities.Compte;
import org.lsi.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Query("SELECT o FROM Operation o WHERE o.compte = :compte ORDER BY o.dateOperation DESC")
    Page<Operation> findByCompte(Compte compte, Pageable pageable);

    Page<Operation> findByCompteCodeCompte(String codeCompte, Pageable pageable);
}