package org.lsi.services;

import java.util.List;
import org.lsi.entities.Client;
import org.lsi.entities.Compte;
import org.lsi.metier.ClientMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientRestService {

    @Autowired
    private ClientMetier clientMetier;

    @PostMapping
    public Client saveClient(@RequestBody Client client) {
        return clientMetier.saveClient(client);
    }

    @GetMapping
    public List<Client> listClients() {
        return clientMetier.listClient();
    }

    @GetMapping("/{codeClient}/comptes")
    public List<Compte> getClientComptes(@PathVariable Long codeClient) {
        return clientMetier.getComptesClient(codeClient);
    }
}
