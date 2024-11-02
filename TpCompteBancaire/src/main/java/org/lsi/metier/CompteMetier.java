package org.lsi.metier;

import org.lsi.entities.Compte;
import org.lsi.entities.Operation;
import org.springframework.data.domain.Page;

public interface CompteMetier {
    public Compte saveCompte(Compte cp);
    public Compte getCompte(String code);
    public Compte verser(String code, double montant);
    public Compte retirer(String code, double montant);
    public Compte virement(String cpte1, String cpte2, double montant);
    public Page<Operation> listOperation(String codeCompte, int page, int size);
}