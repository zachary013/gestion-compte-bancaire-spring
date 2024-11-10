package org.lsi.services;

import java.util.List;

import org.lsi.dto.ClientRequest;
import org.lsi.dto.ClientResponse;
import org.lsi.dto.CompteResponse;
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
    public ResponseEntity<ClientResponse> saveClient(@RequestBody ClientRequest clientRequest) {
        ClientResponse clientResponse = clientMetier.saveClient(clientRequest);
        return ResponseEntity.ok(clientResponse);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> listClient() {
        List<ClientResponse> clients = clientMetier.listClient();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{codeClient}/comptes")
    public ResponseEntity<List<CompteResponse>> getComptesClient(@PathVariable Long codeClient) {
        List<CompteResponse> comptes = clientMetier.getComptesClient(codeClient);
        return ResponseEntity.ok(comptes);
    }

    @GetMapping("/{codeClient}")
    public ResponseEntity<ClientResponse> consulterClient(@PathVariable Long codeClient) {
        ClientResponse clientResponse = clientMetier.getClient(codeClient);
        return ResponseEntity.ok(clientResponse);
    }

    @PutMapping("/{codeClient}")
    public ResponseEntity<?> updateClient(@PathVariable Long codeClient, @RequestBody ClientRequest clientRequest) {
        try {
            ClientResponse updatedClient = clientMetier.updateClient(codeClient, clientRequest);
            return ResponseEntity.ok(updatedClient);
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
