package org.lsi.metier;

import org.lsi.dto.CompteRequest;
import org.lsi.dto.CompteResponse;
import org.lsi.dto.GroupeRequest;
import org.lsi.entities.Compte;
import org.lsi.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompteMetier {
    public CompteResponse saveCompte(CompteRequest cp);
    public void deleteCompte(String id);
    public CompteResponse getCompte(String codeCompte);
    public CompteResponse updateCompte(String id, CompteRequest newCompteData);
    public List<CompteResponse> getAllCompte();
    public CompteResponse verser(String codeCompte, double montant, Long codeEmp);
    public CompteResponse retirer(String codeCompte, double montant, Long codeEmp);
    public CompteResponse virement(String cpte1, String cpte2, double montant, Long codeEmp);
    public Page<Operation> listOperationsByCompte(String codeCompte, Pageable pageable);
}