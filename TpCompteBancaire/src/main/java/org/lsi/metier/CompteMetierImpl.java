package org.lsi.metier;

import org.lsi.dao.ClientRepository;
import org.lsi.dao.CompteRepository;
import org.lsi.dao.EmployeRepository;
import org.lsi.dao.OperationRepository;
import org.lsi.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CompteMetierImpl implements CompteMetier {

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Override
    public Compte saveCompte(Compte cp) {
        Client client = clientRepository.findById(cp.getClient().getCodeClient())
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        // Vérifier si l'employé existe
        Employe employe = employeRepository.findById(cp.getEmploye().getCodeEmploye())
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));
        // Définir la date de création si elle n'est pas spécifiée
        if (cp.getDateCreation() == null) {
            cp.setDateCreation(new Date());
        }
        // Associer le client et l'employé
        cp.setClient(client);
        cp.setEmploye(employe);

        return compteRepository.save(cp);
    }

    @Override
    public Compte getCompte(String code) {
        return compteRepository.findById(code).orElse(null);
    }

    @Override
    public Compte verser(String code, double montant, Long codeEmp) {
        Compte cp = compteRepository.findById(code).orElseThrow(() -> new RuntimeException("Compte not found"));
        Employe e = employeRepository.findById(codeEmp).orElseThrow(() -> new RuntimeException("Employe not found"));
        Operation op = new Versement(new Date(), montant);
        op.setCompte(cp);
        op.setEmploye(e);
        operationRepository.save(op);
        cp.setSolde(cp.getSolde() + montant);
        return compteRepository.save(cp);
    }

    @Override
    public Compte retirer(String code, double montant, Long codeEmp) {
        Compte cp = compteRepository.findById(code).orElseThrow(() -> new RuntimeException("Compte not found"));
        if (cp.getSolde() < montant)
            throw new RuntimeException("Solde insuffisant");
        Employe e = employeRepository.findById(codeEmp).orElseThrow(() -> new RuntimeException("Employe not found"));
        Operation op = new Retrait(new Date(), montant);
        op.setCompte(cp);
        op.setEmploye(e);
        operationRepository.save(op);
        cp.setSolde(cp.getSolde() - montant);
        return compteRepository.save(cp);
    }

    @Override
    public Compte virement(String cpte1, String cpte2, double montant, Long codeEmp) {
        retirer(cpte1, montant, codeEmp);
        verser(cpte2, montant, codeEmp);
        return compteRepository.findById(cpte1).orElse(null);
    }

    @Override
    public Page<Operation> listOperationsByCompte(String codeCompte, Pageable pageable) {
        Compte compte = compteRepository.findById(codeCompte)
                .orElseThrow(() -> new RuntimeException("Compte non trouvé"));
        return operationRepository.findByCompte(compte, pageable);
    }

}