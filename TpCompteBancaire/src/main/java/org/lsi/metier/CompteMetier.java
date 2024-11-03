package org.lsi.metier;

import java.util.List;
import org.lsi.entities.Compte;
import org.lsi.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompteMetier {
    public Compte saveCompte(Compte cp);
    public Compte getCompte(String code);
    public Compte verser(String code, double montant, Long codeEmp);
    public Compte retirer(String code, double montant, Long codeEmp);
    public Compte virement(String cpte1, String cpte2, double montant, Long codeEmp);
    Page<Operation> listOperationsByCompte(String codeCompte, Pageable pageable);
}