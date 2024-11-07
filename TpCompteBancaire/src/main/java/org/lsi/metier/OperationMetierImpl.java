package org.lsi.metier;

import org.lsi.dao.CompteRepository;
import org.lsi.dao.EmployeRepository;
import org.lsi.dao.OperationRepository;
import org.lsi.entities.Compte;
import org.lsi.entities.Operation;
import org.lsi.entities.Retrait;
import org.lsi.entities.Versement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OperationMetierImpl implements OperationMetier {

    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private EmployeRepository employeRepository;

    @Override
    public Operation saveOperation(Operation op) {
        if (op instanceof Versement) {
            Compte compte = op.getCompte();
            compte.setSolde(compte.getSolde() + op.getMontant());
            compteRepository.save(compte);
        } else if (op instanceof Retrait) {
            Compte compte = op.getCompte();
            if (compte.getSolde() < op.getMontant()) {
                throw new RuntimeException("Solde insuffisant");
            }
            compte.setSolde(compte.getSolde() - op.getMontant());
            compteRepository.save(compte);
        }
        op.setDateOperation(new Date());
        return operationRepository.save(op);
    }

    @Override
    public List<Operation> listOperation() {
        return operationRepository.findAll();
    }

    @Override
    public Page<Operation> listOperationCompte(String codeCompte, Pageable pageable) {
        return operationRepository.findByCompteCodeCompte(codeCompte, pageable);
    }
}