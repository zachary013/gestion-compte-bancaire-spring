package org.lsi.metier;

import org.lsi.dao.ClientRepository;
import org.lsi.dao.CompteRepository;
import org.lsi.dao.EmployeRepository;
import org.lsi.dao.OperationRepository;
import org.lsi.dto.CompteRequest;
import org.lsi.dto.CompteResponse;
import org.lsi.dto.EmployeResponse;
import org.lsi.dto.GroupeRequest;
import org.lsi.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public CompteResponse saveCompte(CompteRequest compteRequest) {
        Client client = clientRepository.findById(compteRequest.getCodeClient())
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        Employe employe = employeRepository.findById(compteRequest.getCodeEmploye())
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

        Compte cp;
        if ("CC".equals(compteRequest.getTypeCompte())) {
            CompteCourant courant = new CompteCourant();
            courant.setDecouvert(compteRequest.getDecouvert());
            cp = courant;
        } else if ("CE".equals(compteRequest.getTypeCompte())) {
            CompteEpargne epargne = new CompteEpargne();
            epargne.setTaux(compteRequest.getTaux());
            cp = epargne;
        } else {
            throw new IllegalArgumentException("Type de compte invalide");
        }

        cp.setCodeCompte(UUID.randomUUID().toString());
        cp.setDateCreation(new Date());
        cp.setSolde(compteRequest.getSolde());
        cp.setClient(client);
        cp.setEmploye(employe);

        Compte savedCompte = compteRepository.save(cp);
        return convertToDTO(savedCompte);
    }

    @Override
    public void deleteCompte(String codeCompte) {
        compteRepository.deleteById(codeCompte);
    }

    @Override
    public CompteResponse getCompte(String codeCompte) {
        return convertToDTO(compteRepository.findById(codeCompte)
                .orElseThrow(() -> new RuntimeException("Account not found")));
    }

    @Override
    public CompteResponse updateCompte(String codeCompte, CompteRequest newCompteData) {
        if (codeCompte == null || codeCompte.trim().isEmpty()) {
            throw new IllegalArgumentException("The given id must not be null or empty");
        }

        // Fetch the Compte by its ID
        Compte updatedCompte = compteRepository.findById(codeCompte)
                .map(compte -> {

                    if(newCompteData.getCodeClient() != null){

                        // Update client and employee associations
                        Client client = clientRepository.findById(newCompteData.getCodeClient())
                                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + newCompteData.getCodeClient()));

                        compte.setClient(client);
                    }

                    if(newCompteData.getCodeEmploye() != null){

                        // Update client and employee associations
                        Employe employe = employeRepository.findById(newCompteData.getCodeEmploye())
                                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + newCompteData.getCodeEmploye()));

                        compte.setEmploye(employe);
                    }

                    if(newCompteData.getSolde() != null){
                        compte.setSolde(newCompteData.getSolde());
                    }

                    if(newCompteData.getTypeCompte() != null){

                        // Update specific fields based on the type of Compte
                        if (compte instanceof CompteCourant && "CC".equals(newCompteData.getTypeCompte())) {
                            ((CompteCourant) compte).setDecouvert(newCompteData.getDecouvert());
                        } else if (compte instanceof CompteEpargne && "CE".equals(newCompteData.getTypeCompte())) {
                            ((CompteEpargne) compte).setTaux(newCompteData.getTaux());
                        }
                    }

                    // Save the updated compte
                    return compteRepository.save(compte);
                })
                .orElseThrow(() -> new RuntimeException("Compte not found with ID: " + codeCompte));

        // Return the updated compte as a response DTO
        return convertToDTO(updatedCompte);
    }


    @Override
    public List<CompteResponse> getAllCompte() {
        return  convertToDTO(compteRepository.findAll());
    }





    // operations
    @Override
    public CompteResponse verser(String codeCompte, double montant, Long codeEmp) {
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
            Compte savedCompte = compteRepository.save(compte);
            return convertToDTO(savedCompte);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du versement : " + e.getMessage(), e);
        }
    }

    @Override
    public CompteResponse retirer(String codeCompte, double montant, Long codeEmp) {
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
            Compte savedCompte = compteRepository.save(compte);
            return convertToDTO(savedCompte);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du retrait : " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public CompteResponse virement(String cpte1, String cpte2, double montant, Long codeEmp) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant du virement doit être positif");
        }

        if (cpte1.equals(cpte2)) {
            throw new IllegalArgumentException("Impossible d'effectuer un virement vers le même compte");
        }

        try {
            // Retrait du compte source
            CompteResponse source = retirer(cpte1, montant, codeEmp);

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


    private CompteResponse convertToDTO(Compte compte) {
        CompteResponse dto = new CompteResponse();
        dto.setCodeCompte(compte.getCodeCompte());
        dto.setSolde(compte.getSolde());
        dto.setDateCreation(compte.getDateCreation());
        dto.setCodeClient(compte.getClient().getCodeClient());
        dto.setCodeEmploye(compte.getEmploye().getCodeEmploye());

        if (compte instanceof CompteCourant) {
            dto.setTypeCompte("CC");
            dto.setDecouvert(((CompteCourant) compte).getDecouvert());
        } else if (compte instanceof CompteEpargne) {
            dto.setTypeCompte("CE");
            dto.setTaux(((CompteEpargne) compte).getTaux());
        }

        return dto;
    }

    private List<CompteResponse> convertToDTO(List<Compte> comptes) {
        List<CompteResponse> dtoList = new ArrayList<>();
        for (Compte compte : comptes) {
            CompteResponse dto = convertToDTO(compte);
            dtoList.add(dto);
        }
        return dtoList;
    }


}