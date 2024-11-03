package org.lsi.services;

import org.lsi.entities.Compte;
import org.lsi.entities.Operation;
import org.lsi.metier.CompteMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comptes")
public class CompteRestService {

    @Autowired
    private CompteMetier compteMetier;

    @PostMapping
    public Compte saveCompte(@RequestBody Compte cp) {
        return compteMetier.saveCompte(cp);
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
    public List<Operation> listOperation(@PathVariable String codeCompte) {
        return compteMetier.listOperation(codeCompte);
    }
}