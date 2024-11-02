package org.lsi.metier;

import org.lsi.dao.CompteRepository;
import org.lsi.dao.OperationRepository;
import org.lsi.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class CompteMetierImpl implements CompteMetier {

    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private OperationRepository operationRepository;

    @Override
    public Compte saveCompte(Compte cp) {
        return compteRepository.save(cp);
    }

    @Override
    public Compte getCompte(String code) {
        return compteRepository.findById(code).orElseThrow(() -> new RuntimeException("Compte non trouvé"));
    }

    @Override
    public Compte verser(String code, double montant) {
        Compte cp = getCompte(code);
        Versement v = new Versement(new Date(), montant);
        v.setCompte(cp);
        operationRepository.save(v);
        cp.setSolde(cp.getSolde() + montant);
        return compteRepository.save(cp);
    }

    @Override
    public Compte retirer(String code, double montant) {
        Compte cp = getCompte(code);
        double facilitesCaisse = 0;
        if(cp instanceof CompteCourant)
            facilitesCaisse = ((CompteCourant) cp).getDecouvert();
        if(cp.getSolde() + facilitesCaisse < montant)
            throw new RuntimeException("Solde insuffisant");
        Retrait r = new Retrait(new Date(), montant);
        r.setCompte(cp);
        operationRepository.save(r);
        cp.setSolde(cp.getSolde() - montant);
        return compteRepository.save(cp);
    }

    @Override
    public Compte virement(String cpte1, String cpte2, double montant) {
        retirer(cpte1, montant);
        return verser(cpte2, montant);
    }

    @Override
    public Page<Operation> listOperation(String codeCompte, int page, int size) {
        return operationRepository.listOperation(codeCompte, PageRequest.of(page, size));
    }
}