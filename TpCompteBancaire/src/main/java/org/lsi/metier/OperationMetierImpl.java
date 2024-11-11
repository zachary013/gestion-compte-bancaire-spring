package org.lsi.metier;

import org.lsi.dao.CompteRepository;
import org.lsi.dao.EmployeRepository;
import org.lsi.dao.OperationRepository;
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
                .orElseThrow(() -> new RuntimeException("Compte source non trouvé"));

        Employe employe = employeRepository.findById(request.getCodeEmploye())
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

        Operation operation;
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

        Operation savedOperation = operationRepository.save(operation);
        compteRepository.save(compte);
        return convertToOperationDTO(savedOperation);
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

        compte.setSolde(compte.getSolde() + request.getMontant());
        compteRepository.save(compte);

        Operation savedOperation = operationRepository.save(versement);
        return convertToOperationDTO(savedOperation);
    }


    @Override
    public OperationResponse retirer(OperationRequest request) {
        if (request.getMontant() <= 0) {
            throw new IllegalArgumentException("Le montant du retrait doit être positif");
        }

        Compte compte = compteRepository.findById(request.getCodeCompte())
                .orElseThrow(() -> new RuntimeException("Compte non trouvé"));

        if (compte.getSolde() < request.getMontant()) {
            throw new RuntimeException("Solde insuffisant");
        }

        Employe employe = employeRepository.findById(request.getCodeEmploye())
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

        Retrait retrait = new Retrait(new Date(), request.getMontant());
        retrait.setCompte(compte);
        retrait.setEmploye(employe);

        compte.setSolde(compte.getSolde() - request.getMontant());
        compteRepository.save(compte);

        Operation savedOperation = operationRepository.save(retrait);
        return convertToOperationDTO(savedOperation);
    }


    @Override
    @Transactional
    public OperationResponse virement(OperationRequest request) {
        if (request.getMontant() <= 0) {
            throw new IllegalArgumentException("Le montant du virement doit être positif");
        }

        if (request.getCodeCompte().equals(request.getCodeCompteDest())) {
            throw new IllegalArgumentException("Le compte source et destination ne peuvent pas être identiques");
        }

        // Get source account
        Compte compteSource = compteRepository.findById(request.getCodeCompte())
                .orElseThrow(() -> new RuntimeException("Compte source non trouvé"));

        // Get destination account
        Compte compteDest = compteRepository.findById(request.getCodeCompteDest())
                .orElseThrow(() -> new RuntimeException("Compte destination non trouvé"));

        // Check if source account has sufficient balance
        if (compteSource.getSolde() < request.getMontant()) {
            throw new RuntimeException("Solde insuffisant pour effectuer le virement");
        }

        Employe employe = employeRepository.findById(request.getCodeEmploye())
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

        // Create retrait operation for source account
        Retrait retrait = new Retrait(new Date(), request.getMontant());
        retrait.setCompte(compteSource);
        retrait.setCompteDestination(compteDest);
        retrait.setEmploye(employe);

        // Create versement operation for destination account
        Versement versement = new Versement(new Date(), request.getMontant());
        versement.setCompte(compteDest);
        versement.setCompteDestination(compteSource);
        versement.setEmploye(employe);

        // Update balances
        compteSource.setSolde(compteSource.getSolde() - request.getMontant());
        compteDest.setSolde(compteDest.getSolde() + request.getMontant());

        // Save everything
        compteRepository.save(compteSource);
        compteRepository.save(compteDest);
        operationRepository.save(retrait);
        Operation savedOperation = operationRepository.save(versement);

        return convertToOperationDTO(savedOperation);
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

    @Override
    public List<OperationResponse> getOperationsByEmploye(Long codeEmploye) {
        Employe employe = employeRepository.findById(codeEmploye)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

        List<Operation> operations = operationRepository.findByEmploye(employe);

        return operations.stream()
                .map(this::convertToOperationDTO)
                .toList();
    }


    private OperationResponse convertToOperationDTO(Operation operation) {
        OperationResponse response = new OperationResponse();
        response.setCodeOperation(operation.getNumeroOperation());
        response.setDateOperation(operation.getDateOperation());
        response.setMontant(operation.getMontant());

        if (operation instanceof Versement) {
            response.setType("VERSEMENT");
        } else if (operation instanceof Retrait) {
            response.setType("RETRAIT");
        }

        if (operation.getCompte() != null) {
            response.setCodeCompte(operation.getCompte().getCodeCompte());
            response.setSoldeApresOperation(operation.getCompte().getSolde());
        }

        if (operation.getCompteDestination() != null) {
            response.setCodeCompteDest(operation.getCompteDestination().getCodeCompte());
        }

        if (operation.getEmploye() != null) {
            response.setNomEmploye(operation.getEmploye().getNomEmploye());
        }

        return response;
    }

}
