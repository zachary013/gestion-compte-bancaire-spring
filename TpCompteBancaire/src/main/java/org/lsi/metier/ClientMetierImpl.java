package org.lsi.metier;

import java.util.ArrayList;
import java.util.List;
import org.lsi.dao.ClientRepository;
import org.lsi.entities.Client;
import org.lsi.entities.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientMetierImpl implements ClientMetier {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client saveClient(Client c) {
        return clientRepository.save(c);
    }

    @Override
    public List<Client> listClient() {
        return clientRepository.findAll();
    }

    @Override
    public List<Compte> getComptesClient(Long codeClient) {
        Client client = clientRepository.findById(codeClient)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        return new ArrayList<>(client.getComptes());
    }
}
