package org.lsi.metier;

import org.lsi.dao.CompteRepository;
import org.lsi.dao.EmployeRepository;
import org.lsi.dao.OperationRepository;
import org.lsi.dto.CompteResponse;
import org.lsi.dto.OperationRequest;
import org.lsi.dto.OperationResponse;
import org.lsi.entities.Compte;
import org.lsi.entities.Employe;
import org.lsi.entities.Operation;
import org.lsi.entities.Retrait;
import org.lsi.entities.Versement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OperationMetierImpl implements OperationMetier {

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Override
    public OperationResponse saveOperation(OperationRequest request) {
        Compte compte = compteRepository.findById(request.getCodeCompte())
                .orElseThrow(() -> new RuntimeException("Compte non trouvé"));

        Employe employe = employeRepository.findById(request.getCodeEmploye())
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

        Operation operation;
        // Create appropriate operation type based on request
        if ("VERSEMENT".equals(request.getTypeOperation())) {
            operation = new Versement();
            compte.setSolde(compte.getSolde() + request.getMontant());
        } else if ("RETRAIT".equals(request.getTypeOperation())) {
            if (compte.getSolde() < request.getMontant()) {
                throw new RuntimeException("Solde insuffisant");
            }
            operation = new Retrait();
            compte.setSolde(compte.getSolde() - request.getMontant());
        } else {
            throw new IllegalArgumentException("Type d'opération invalide");
        }

        operation.setDateOperation(new Date());
        operation.setMontant(request.getMontant());
        operation.setCompte(compte);
        operation.setEmploye(employe);

        compteRepository.save(compte);
        return convertToOperationDTO(operationRepository.save(operation));
    }

    @Override
    public List<OperationResponse> listOperation() {
        return operationRepository.findAll().stream()
                .map(this::convertToOperationDTO)
                .toList();
    }

    @Override
    public OperationResponse verser(OperationRequest request) {
        if (request.getMontant() <= 0) {
            throw new IllegalArgumentException("Le montant du versement doit être positif");
        }

        Compte compte = compteRepository.findById(request.getCodeCompte())
                .orElseThrow(() -> new RuntimeException("Compte non trouvé"));
        Employe employe = employeRepository.findById(request.getCodeEmploye())
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

        Versement versement = new Versement(new Date(), request.getMontant());
        versement.setCompte(compte);
        versement.setEmploye(employe);
        operationRepository.save(versement);

        compte.setSolde(compte.getSolde() + request.getMontant());
        compteRepository.save(compte);

        return convertToOperationDTO(versement);
    }


    @Override
    public OperationResponse retirer(OperationRequest request) {
        if (request.getMontant() <= 0) {
            throw new IllegalArgumentException("Le montant du retrait doit être positif");
        }

        Compte compte = compteRepository.findById(request.getCodeCompte())
                .orElseThrow(() -> new RuntimeException("Compte non trouvé"));
        Employe employe = employeRepository.findById(request.getCodeEmploye())
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

        if (compte.getSolde() < request.getMontant()) {
            throw new RuntimeException("Solde insuffisant");
        }

        Retrait retrait = new Retrait(new Date(), request.getMontant());
        retrait.setCompte(compte);
        retrait.setEmploye(employe);
        operationRepository.save(retrait);

        compte.setSolde(compte.getSolde() - request.getMontant());
        compteRepository.save(compte);

        return convertToOperationDTO(retrait);
    }


    @Override
    @Transactional
    public OperationResponse virement(OperationRequest request) {
        // Vérifiez et retirez le montant du compte source
        OperationRequest retraitRequest = new OperationRequest();
        retraitRequest.setCodeCompte(request.getCodeCompte1());
        retraitRequest.setMontant(request.getMontant());
        retraitRequest.setCodeEmploye(request.getCodeEmploye());
        retirer(retraitRequest);

        // Versez le montant dans le compte de destination
        OperationRequest versementRequest = new OperationRequest();
        versementRequest.setCodeCompte(request.getCodeCompte2());
        versementRequest.setMontant(request.getMontant());
        versementRequest.setCodeEmploye(request.getCodeEmploye());
        return verser(versementRequest);
    }


    @Override
    public List<OperationResponse> getOperationsByCompte(String codeCompte) {
        Compte compte = compteRepository.findById(codeCompte)
                .orElseThrow(() -> new RuntimeException("Compte non trouvé"));

        List<Operation> operations = operationRepository.findByCompte(compte);

        return operations.stream()
                .map(this::convertToOperationDTO)
                .toList();
    }

    private OperationResponse convertToOperationDTO(Operation operation) {
        OperationResponse operationResponse = new OperationResponse();
        operationResponse.setCodeOperation(operation.getNumeroOperation());
        operationResponse.setDateOperation(operation.getDateOperation());
        operationResponse.setMontant(operation.getMontant());

        if (operation instanceof Versement) {
            operationResponse.setType("VERSEMENT");
        } else if (operation instanceof Retrait) {
            operationResponse.setType("RETRAIT");
        }

        if (operation.getCompte() != null) {
            operationResponse.setCodeCompte(operation.getCompte().getCodeCompte());
            operationResponse.setSoldeApresOperation(operation.getCompte().getSolde());
        }

        if (operation.getEmploye() != null) {
            operationResponse.setNomEmploye(operation.getEmploye().getNomEmploye());
        }

        return operationResponse;
    }

}
