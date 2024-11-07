package org.lsi.metier;

import org.lsi.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OperationMetier {
    Operation saveOperation(Operation op);
    List<Operation> listOperation();
    Page<Operation> listOperationCompte(String codeCompte, Pageable pageable);
}