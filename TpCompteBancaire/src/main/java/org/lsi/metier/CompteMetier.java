package org.lsi.metier;

import org.lsi.dto.CompteRequest;
import org.lsi.dto.CompteResponse;
import org.lsi.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompteMetier {
    public CompteResponse saveCompte(CompteRequest cp);
    public CompteResponse getCompte(String codeCompte);
    public CompteResponse updateCompte(String id, CompteRequest newCompteData);
    public void deleteCompte(String id);
    public List<CompteResponse> getAllCompte();
}