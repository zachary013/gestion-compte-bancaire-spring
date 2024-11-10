package org.lsi.services;

import org.lsi.dto.OperationRequest;
import org.lsi.dto.OperationResponse;
import org.lsi.metier.OperationMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operations")
@CrossOrigin("*")
public class OperationRestService {

    @Autowired
    private OperationMetier operationMetier;

    @PostMapping("/versement")
    public ResponseEntity<OperationResponse> saveVersement(@RequestBody OperationRequest request) {
        request.setTypeOperation("VERSEMENT");
        return ResponseEntity.ok(operationMetier.saveOperation(request));
    }

    @PostMapping("/retrait")
    public ResponseEntity<OperationResponse> saveRetrait(@RequestBody OperationRequest request) {
        request.setTypeOperation("RETRAIT");
        return ResponseEntity.ok(operationMetier.saveOperation(request));
    }

    @PostMapping("/virement")
    public ResponseEntity<OperationResponse> virement(@RequestBody OperationRequest request) {
        return ResponseEntity.ok(operationMetier.virement(request));
    }

    @GetMapping
    public ResponseEntity<List<OperationResponse>> listOperations() {
        return ResponseEntity.ok(operationMetier.listOperation());
    }

    @GetMapping("/{codeCompte}")
    public ResponseEntity<List<OperationResponse>> getOperationsByCompte(@PathVariable String codeCompte) {
        return ResponseEntity.ok(operationMetier.getOperationsByCompte(codeCompte));
    }

    @PostMapping("/verser")
    public ResponseEntity<OperationResponse> verser(@RequestBody OperationRequest request) {
        return ResponseEntity.ok(operationMetier.verser(request));
    }

    @PostMapping("/retirer")
    public ResponseEntity<OperationResponse> retirer(@RequestBody OperationRequest request) {
        return ResponseEntity.ok(operationMetier.retirer(request));
    }
}