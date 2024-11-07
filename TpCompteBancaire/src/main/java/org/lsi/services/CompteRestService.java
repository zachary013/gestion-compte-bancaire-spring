package org.lsi.services;

import org.lsi.entities.Compte;
import org.lsi.entities.CompteCourant;
import org.lsi.entities.CompteEpargne;
import org.lsi.entities.Operation;
import org.lsi.metier.CompteMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comptes")
public class CompteRestService {

    @Autowired
    private CompteMetier compteMetier;

    @PostMapping
    public ResponseEntity<Compte> saveCompte(@RequestBody Map<String, Object> requestData) {
        String typeCompte = (String) requestData.get("typeCompte");
        Long codeClient = Long.valueOf((Integer) requestData.get("codeClient"));
        Long codeEmploye = Long.valueOf((Integer) requestData.get("codeEmploye"));

        Compte cp;
        if ("CE".equals(typeCompte)) {
            cp = new CompteEpargne();
            Double taux = ((Number) requestData.get("taux")).doubleValue();
            ((CompteEpargne) cp).setTaux(taux);
        } else {
            cp = new CompteCourant();
            Double decouvert = ((Number) requestData.get("decouvert")).doubleValue();
            ((CompteCourant) cp).setDecouvert(decouvert);
        }

        cp = compteMetier.saveCompte(cp, codeClient, codeEmploye);
        return ResponseEntity.ok(cp);
    }

    @GetMapping("/{code}")
    public Compte getCompte(@PathVariable String code) {
        return compteMetier.getCompte(code);
    }

    @PostMapping("/verser")
    public Compte verser(@RequestParam String code, @RequestParam double montant, @RequestParam Long codeEmp) {
        return compteMetier.verser(code, montant, codeEmp);
    }

    @PostMapping("/retirer")
    public Compte retirer(@RequestParam String code, @RequestParam double montant, @RequestParam Long codeEmp) {
        return compteMetier.retirer(code, montant, codeEmp);
    }

    @PostMapping("/virement")
    public Compte virement(@RequestParam String cpte1, @RequestParam String cpte2, @RequestParam double montant, @RequestParam Long codeEmp) {
        return compteMetier.virement(cpte1, cpte2, montant, codeEmp);
    }

    @GetMapping("/{codeCompte}/operations")
    public Page<Operation> listOperation(@PathVariable String codeCompte, Pageable pageable) {
        return compteMetier.listOperationsByCompte(codeCompte, pageable);
    }
}