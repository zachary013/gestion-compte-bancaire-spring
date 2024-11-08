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

import java.util.Date;
import java.util.UUID;

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
    public Compte saveCompte(Compte cp, Long codeClient, Long codeEmploye) {
        Client client = clientRepository.findById(codeClient)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        Employe employe = employeRepository.findById(codeEmploye)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

        cp.setCodeCompte(UUID.randomUUID().toString());
        cp.setDateCreation(new Date());
        cp.setClient(client);
        cp.setEmploye(employe);

        return compteRepository.save(cp);
    }

    @Override
    public Compte getCompte(String codeCompte) {
        return compteRepository.findById(codeCompte)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Override
    public Compte verser(String codeCompte, double montant, Long codeEmp) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant du versement doit être positif");
        }

        try {
            Compte compte = compteRepository.findById(codeCompte)
                    .orElseThrow(() -> new RuntimeException("Compte " + codeCompte + " non trouvé"));

            Employe employe = employeRepository.findById(codeEmp)
                    .orElseThrow(() -> new RuntimeException("Employé " + codeEmp + " non trouvé"));

            // Création et sauvegarde de l'opération
            Versement versement = new Versement(new Date(), montant);
            versement.setCompte(compte);
            versement.setEmploye(employe);
            operationRepository.save(versement);

            // Mise à jour du solde
            compte.setSolde(compte.getSolde() + montant);
            return compteRepository.save(compte);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du versement : " + e.getMessage(), e);
        }
    }

    @Override
    public Compte retirer(String codeCompte, double montant, Long codeEmp) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant du retrait doit être positif");
        }

        try {
            Compte compte = compteRepository.findById(codeCompte)
                    .orElseThrow(() -> new RuntimeException("Compte " + codeCompte + " non trouvé"));

            Employe employe = employeRepository.findById(codeEmp)
                    .orElseThrow(() -> new RuntimeException("Employé " + codeEmp + " non trouvé"));

            // Vérification du solde avec gestion spéciale pour CompteCourant
            if (compte instanceof CompteCourant) {
                CompteCourant cc = (CompteCourant) compte;
                if (compte.getSolde() + cc.getDecouvert() < montant) {
                    throw new RuntimeException("Solde insuffisant (y compris découvert autorisé)");
                }
            } else if (compte.getSolde() < montant) {
                throw new RuntimeException("Solde insuffisant");
            }

            // Création et sauvegarde de l'opération
            Retrait retrait = new Retrait(new Date(), montant);
            retrait.setCompte(compte);
            retrait.setEmploye(employe);
            operationRepository.save(retrait);

            // Mise à jour du solde
            compte.setSolde(compte.getSolde() - montant);
            return compteRepository.save(compte);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du retrait : " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Compte virement(String cpte1, String cpte2, double montant, Long codeEmp) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant du virement doit être positif");
        }

        if (cpte1.equals(cpte2)) {
            throw new IllegalArgumentException("Impossible d'effectuer un virement vers le même compte");
        }

        try {
            // Retrait du compte source
            Compte source = retirer(cpte1, montant, codeEmp);

            // Versement sur le compte destination
            verser(cpte2, montant, codeEmp);

            return source;

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du virement : " + e.getMessage(), e);
        }
    }

    @Override
    public Page<Operation> listOperationsByCompte(String codeCompte, Pageable pageable) {
        Compte compte = compteRepository.findById(codeCompte)
                .orElseThrow(() -> new RuntimeException("Compte non trouvé"));
        return operationRepository.findByCompte(compte, pageable);
    }

}