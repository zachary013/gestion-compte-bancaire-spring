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

    @Override
    public Client consulterClient(Long codeClient) {
        return clientRepository.findById(codeClient)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec le code: " + codeClient));
    }

    @Override
    public Client updateClient(Long codeClient, Client updatedClient) {
        Client client = consulterClient(codeClient);
        client.setNomClient(updatedClient.getNomClient() != null ? updatedClient.getNomClient() : client.getNomClient());
        client.setEmail(updatedClient.getEmail() != null ? updatedClient.getEmail() : client.getEmail()); // Add this line
        client.setDateNaissance(updatedClient.getDateNaissance() != null ? updatedClient.getDateNaissance() : client.getDateNaissance());
        client.setTelephone(updatedClient.getTelephone() != null ? updatedClient.getTelephone() : client.getTelephone());
        client.setAdresse(updatedClient.getAdresse() != null ? updatedClient.getAdresse() : client.getAdresse());
        client.setVille(updatedClient.getVille() != null ? updatedClient.getVille() : client.getVille());
        client.setPays(updatedClient.getPays() != null ? updatedClient.getPays() : client.getPays());
        return clientRepository.save(client);
    }

    @Override
    public void deleteClient(Long codeClient) {
        if (!clientRepository.existsById(codeClient)) {
            throw new RuntimeException("Client non trouvé avec le code: " + codeClient);
        }
        clientRepository.deleteById(codeClient);
    }
}
