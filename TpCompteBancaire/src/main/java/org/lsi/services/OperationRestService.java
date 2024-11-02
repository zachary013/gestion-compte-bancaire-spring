package org.lsi.services;

import org.lsi.entities.Operation;
import org.lsi.metier.OperationMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/operations")
public class OperationRestService {
    @Autowired
    private OperationMetier operationMetier;

    @PostMapping
    public Operation saveOperation(@RequestBody Operation op) {
        return operationMetier.saveOperation(op);
    }

    @GetMapping
    public List<Operation> listOperations() {
        return operationMetier.listOperations();
    }

    @GetMapping("/compte/{codeCompte}")
    public List<Operation> listOperationsByCompte(@PathVariable String codeCompte) {
        return operationMetier.listOperationsByCompte(codeCompte);
    }
}