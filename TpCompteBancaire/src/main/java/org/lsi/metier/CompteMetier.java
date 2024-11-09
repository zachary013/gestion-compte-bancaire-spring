package org.lsi.metier;

import org.lsi.entities.Compte;
import org.lsi.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompteMetier {
    public Compte saveCompte(Compte cp, Long codeClient, Long codeEmploye);
    public Compte getCompte(String codeCompte);
    public List<Compte> getAllCompte();
    public Compte verser(String codeCompte, double montant, Long codeEmp);
    public Compte retirer(String codeCompte, double montant, Long codeEmp);
    public Compte virement(String cpte1, String cpte2, double montant, Long codeEmp);
    public Page<Operation> listOperationsByCompte(String codeCompte, Pageable pageable);
}