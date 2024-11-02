package org.lsi.services;

import org.lsi.entities.Compte;
import org.lsi.entities.CompteCourant;
import org.lsi.entities.CompteEpargne;
import org.lsi.entities.Operation;
import org.lsi.metier.CompteMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/comptes")
public class CompteRestService {

    @Autowired
    private CompteMetier compteMetier;

    @PostMapping
    public Compte saveCompte(@RequestBody Map<String, Object> requestBody) {
        String type = (String) requestBody.get("type"); // Expecting "CC" for CompteCourant or "CE" for CompteEpargne
        String codeCompte = (String) requestBody.get("codeCompte");
        Date dateCreation = new Date(); // or parse the date from requestBody if provided
        double solde = Double.parseDouble(requestBody.get("solde").toString());

        Compte cp;
        if ("CC".equalsIgnoreCase(type)) {
            double decouvert = Double.parseDouble(requestBody.get("decouvert").toString());
            cp = new CompteCourant(codeCompte, dateCreation, solde, decouvert);
        } else if ("CE".equalsIgnoreCase(type)) {
            double taux = Double.parseDouble(requestBody.get("taux").toString());
            cp = new CompteEpargne(codeCompte, dateCreation, solde, taux);
        } else {
            throw new IllegalArgumentException("Invalid account type");
        }

        return compteMetier.saveCompte(cp);
    }

    @GetMapping("/{code}")
    public Compte getCompte(@PathVariable String code) {
        return compteMetier.getCompte(code);
    }

    @PutMapping("/verser")
    public Compte verser(@RequestParam String code, @RequestParam double montant) {
        return compteMetier.verser(code, montant);
    }

    @PutMapping("/retirer")
    public Compte retirer(@RequestParam String code, @RequestParam double montant) {
        return compteMetier.retirer(code, montant);
    }

    @PutMapping("/virement")
    public Compte virement(@RequestParam String cpte1, @RequestParam String cpte2, @RequestParam double montant) {
        return compteMetier.virement(cpte1, cpte2, montant);
    }

    @GetMapping("/{codeCompte}/operations")
    public Page<Operation> listOperation(
            @PathVariable String codeCompte,
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="5") int size) {
        return compteMetier.listOperation(codeCompte, page, size);
    }
}