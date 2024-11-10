package org.lsi.services;

import org.lsi.dto.CompteRequest;
import org.lsi.dto.CompteResponse;
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
@CrossOrigin(origins = "http://localhost:4200")
public class CompteRestService {

    @Autowired
    private CompteMetier compteMetier;

    @PostMapping
    public CompteResponse saveCompte(@RequestBody CompteRequest compteRequest) {
        return compteMetier.saveCompte(compteRequest);
    }

    @DeleteMapping("/{codeCompte}")
    public void deleteCompte(@PathVariable String codeCompte) {
        compteMetier.deleteCompte(codeCompte);
    }

    @PutMapping("/{codeCompte}")
    public CompteResponse updateCompte(@RequestBody CompteRequest compteRequest, @PathVariable String codeCompte) {
        return compteMetier.updateCompte((String) codeCompte, compteRequest);
    }



    @GetMapping
    public List<CompteResponse> getAllCompte (){
        return compteMetier.getAllCompte();
    }

    @GetMapping("/{code}")
    public CompteResponse getCompte (@PathVariable String code){
        return compteMetier.getCompte(code);
    }

    @PostMapping("/verser")
    public CompteResponse verser (@RequestParam String code,@RequestParam double montant, @RequestParam Long codeEmp){
        return compteMetier.verser(code, montant, codeEmp);
    }

    @PostMapping("/retirer")
    public CompteResponse retirer (@RequestParam String code,@RequestParam double montant, @RequestParam Long codeEmp){
        return compteMetier.retirer(code, montant, codeEmp);
    }

    @PostMapping("/virement")
    public CompteResponse virement (@RequestParam String cpte1, @RequestParam String cpte2,@RequestParam double montant,
    @RequestParam Long codeEmp){
        return compteMetier.virement(cpte1, cpte2, montant, codeEmp);
    }

    @GetMapping("/{codeCompte}/operations")
    public Page<Operation> listOperation (@PathVariable String codeCompte, Pageable pageable){
        return compteMetier.listOperationsByCompte(codeCompte, pageable);
    }

}