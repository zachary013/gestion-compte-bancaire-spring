package org.lsi.services;

import org.lsi.dto.CompteRequest;
import org.lsi.dto.CompteResponse;
import org.lsi.metier.CompteMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{code}")
    public CompteResponse getCompte (@PathVariable String code){
        return compteMetier.getCompte(code);
    }

    @PutMapping("/{codeCompte}")
    public CompteResponse updateCompte(@RequestBody CompteRequest compteRequest, @PathVariable String codeCompte) {
        return compteMetier.updateCompte((String) codeCompte, compteRequest);
    }

    @DeleteMapping("/{codeCompte}")
    public void deleteCompte(@PathVariable String codeCompte) {
        compteMetier.deleteCompte(codeCompte);
    }

    @GetMapping
    public List<CompteResponse> getAllCompte (){
        return compteMetier.getAllCompte();
    }

}