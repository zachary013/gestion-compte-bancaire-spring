package org.lsi.metier;

import org.lsi.dao.ClientRepository;
import org.lsi.dao.CompteRepository;
import org.lsi.dao.EmployeRepository;
import org.lsi.dao.OperationRepository;
import org.lsi.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CompteMetierImpl implements CompteMetier {

    private static final Logger log = LoggerFactory.getLogger(CompteMetierImpl.class);
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

        log.info("de");
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
    public List<Compte> getAllCompte() {
        return  compteRepository.findAll();
    }


    @Override
    public Compte verser(String codeCompte, double montant, Long codeEmp) {
        try {
            Compte cp = compteRepository.findById(codeCompte)
                    .orElseThrow(() -> new RuntimeException("Compte not found"));
            Employe e = employeRepository.findById(codeEmp)
                    .orElseThrow(() -> new RuntimeException("Employe not found"));
            Operation op = new Versement(new Date(), montant);
            op.setCompte(cp);
            op.setEmploye(e);
            operationRepository.save(op);

            cp.setSolde(cp.getSolde() + montant);
            return compteRepository.save(cp);
        } catch (Exception ex) {
            throw new RuntimeException("Erreur lors du versement : " + ex.getMessage());
        }
    }

    @Override
    public Compte retirer(String codeCompte, double montant, Long codeEmp) {
        Compte cp = compteRepository.findById(codeCompte)
                .orElseThrow(() -> new RuntimeException("Compte not found"));
        if (cp.getSolde() < montant) {
            throw new RuntimeException("Solde insuffisant pour effectuer le retrait");
        }
        Employe e = employeRepository.findById(codeEmp)
                .orElseThrow(() -> new RuntimeException("Employe not found"));
        Operation op = new Retrait(new Date(), montant);
        op.setCompte(cp);
        op.setEmploye(e);
        operationRepository.save(op);

        cp.setSolde(cp.getSolde() - montant);
        return compteRepository.save(cp);
    }

    @Override
    @Transactional
    public Compte virement(String cpte1, String cpte2, double montant, Long codeEmp) {
        retirer(cpte1, montant, codeEmp);
        verser(cpte2, montant, codeEmp);
        return compteRepository.findById(cpte1).orElse(null);  // retourne le compte source après virement
    }

    @Override
    public Page<Operation> listOperationsByCompte(String codeCompte, Pageable pageable) {
        Compte compte = compteRepository.findById(codeCompte)
                .orElseThrow(() -> new RuntimeException("Compte non trouvé"));
        return operationRepository.findByCompte(compte, pageable);
    }

}