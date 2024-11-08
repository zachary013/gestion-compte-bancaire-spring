package org.lsi.services;

import java.util.List;
import org.lsi.entities.Client;
import org.lsi.entities.Compte;
import org.lsi.metier.ClientMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientRestService {

    @Autowired
    private ClientMetier clientMetier;

    @PostMapping
    public Client saveClient(@RequestBody Client client) {
        return clientMetier.saveClient(client);
    }

    @GetMapping
    public List<Client> listClient() {
        return clientMetier.listClient();
    }

    @GetMapping("/{codeClient}/comptes")
    public List<Compte> getComptesClient(@PathVariable Long codeClient) {
        return clientMetier.getComptesClient(codeClient);
    }
}
