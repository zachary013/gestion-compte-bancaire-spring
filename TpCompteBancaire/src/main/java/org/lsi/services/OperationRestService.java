package org.lsi.services;

import org.lsi.entities.Operation;
import org.lsi.entities.Versement;
import org.lsi.entities.Retrait;
import org.lsi.metier.OperationMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operations")
@CrossOrigin("*")
public class OperationRestService {

    @Autowired
    private OperationMetier operationMetier;

    @PostMapping("/versement")
    public Operation saveVersement(@RequestBody Versement versement) {
        return operationMetier.saveOperation(versement);
    }

    @PostMapping("/retrait")
    public Operation saveRetrait(@RequestBody Retrait retrait) {
        return operationMetier.saveOperation(retrait);
    }

    @GetMapping
    public List<Operation> listOperations() {
        return operationMetier.listOperation();
    }

    @GetMapping("/compte/{codeCompte}")
    public Page<Operation> listOperationsCompte(@PathVariable String codeCompte, Pageable pageable) {
        return operationMetier.listOperationCompte(codeCompte, pageable);
    }
}