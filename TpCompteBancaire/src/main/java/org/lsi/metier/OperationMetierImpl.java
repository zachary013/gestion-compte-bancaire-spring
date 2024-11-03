package org.lsi.metier;

import org.lsi.dao.OperationRepository;
import org.lsi.entities.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OperationMetierImpl implements OperationMetier {

    @Autowired
    private OperationRepository operationRepository;

    @Override
    public Operation saveOperation(Operation op) {
        return operationRepository.save(op);
    }

    //retourner toutes les operations de la BD
    @Override
    public List<Operation> listOperations() {
        return operationRepository.findAll();
    }
}