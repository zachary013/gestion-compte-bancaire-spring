package org.lsi.services;

import java.util.List;
import org.lsi.entities.Client;
import org.lsi.entities.Compte;
import org.lsi.metier.ClientMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientRestService {

    @Autowired
    private ClientMetier clientMetier;

    @PostMapping
    public ResponseEntity<Client> saveClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientMetier.saveClient(client));
    }

    @GetMapping
    public ResponseEntity<List<Client>> listClient() {
        return ResponseEntity.ok(clientMetier.listClient());
    }

    @GetMapping("/{codeClient}/comptes")
    public ResponseEntity<List<Compte>> getComptesClient(@PathVariable Long codeClient) {
        return ResponseEntity.ok(clientMetier.getComptesClient(codeClient));
    }

    @GetMapping("/{codeClient}")
    public ResponseEntity<Client> consulterClient(@PathVariable Long codeClient) {
        return ResponseEntity.ok(clientMetier.consulterClient(codeClient));
    }

    @PutMapping("/{codeClient}")
    public ResponseEntity<?> updateClient(@PathVariable Long codeClient, @RequestBody Client client) {
        try {
            return ResponseEntity.ok(clientMetier.updateClient(codeClient, client));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @DeleteMapping("/{codeClient}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long codeClient) {
        clientMetier.deleteClient(codeClient);
        return ResponseEntity.noContent().build();
    }
}
