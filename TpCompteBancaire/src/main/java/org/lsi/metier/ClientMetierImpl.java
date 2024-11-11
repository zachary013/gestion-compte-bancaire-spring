package org.lsi.metier;

import org.lsi.dao.ClientRepository;
import org.lsi.dto.ClientRequest;
import org.lsi.dto.ClientResponse;
import org.lsi.dto.CompteResponse;
import org.lsi.entities.Client;
import org.lsi.entities.Compte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientMetierImpl implements ClientMetier {

    private static final Logger log = LoggerFactory.getLogger(ClientMetierImpl.class);

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientResponse saveClient(ClientRequest clientRequest) {
        try {
            Client client = new Client();
            client.setNomClient(clientRequest.getNomClient());
            client.setEmail(clientRequest.getEmail());
            client.setDateNaissance(clientRequest.getDateNaissance());
            client.setTelephone(clientRequest.getTelephone());
            client.setAdresse(clientRequest.getAdresse());
            client.setVille(clientRequest.getVille());
            client.setPays(clientRequest.getPays());

            Client savedClient = clientRepository.save(client);
            return convertToDTO(savedClient);
        } catch (Exception e) {
            log.error("Erreur lors de la sauvegarde du client", e);
            throw new RuntimeException("Erreur lors de la sauvegarde du client: " + e.getMessage());
        }
    }

    @Override
    public List<ClientResponse> listClient() {
        try {
            List<Client> clients = clientRepository.findAll();
            return clients.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de la liste des clients", e);
            throw new RuntimeException("Erreur lors de la récupération des clients: " + e.getMessage());
        }
    }

    @Override
    public ClientResponse getClient(Long codeClient) {
        try {
            Client client = clientRepository.findById(codeClient)
                    .orElseThrow(() -> new RuntimeException("Client non trouvé avec le code: " + codeClient));
            return convertToDTO(client);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du client {}", codeClient, e);
            throw new RuntimeException("Erreur lors de la récupération du client: " + e.getMessage());
        }
    }

    @Override
    public List<CompteResponse> getComptesClient(Long codeClient) {
        Client client = clientRepository.findById(codeClient)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec le code: " + codeClient));

        // Convert Compte entities to CompteResponse DTOs
        return client.getComptes().stream()
                .map(this::convertCompteToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public ClientResponse updateClient(Long codeClient, ClientRequest clientRequest) {
        try {
            Client client = clientRepository.findById(codeClient)
                    .orElseThrow(() -> new RuntimeException("Client non trouvé avec le code: " + codeClient));

            if (clientRequest.getNomClient() != null) {
                client.setNomClient(clientRequest.getNomClient());
            }
            if (clientRequest.getEmail() != null) {
                client.setEmail(clientRequest.getEmail());
            }
            if (clientRequest.getDateNaissance() != null) {
                client.setDateNaissance(clientRequest.getDateNaissance());
            }
            if (clientRequest.getTelephone() != null) {
                client.setTelephone(clientRequest.getTelephone());
            }
            if (clientRequest.getAdresse() != null) {
                client.setAdresse(clientRequest.getAdresse());
            }
            if (clientRequest.getVille() != null) {
                client.setVille(clientRequest.getVille());
            }
            if (clientRequest.getPays() != null) {
                client.setPays(clientRequest.getPays());
            }

            Client updatedClient = clientRepository.save(client);
            return convertToDTO(updatedClient);
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour du client {}", codeClient, e);
            throw new RuntimeException("Erreur lors de la mise à jour du client: " + e.getMessage());
        }
    }

    @Override
    public void deleteClient(Long codeClient) {
        try {
            Client client = clientRepository.findById(codeClient)
                    .orElseThrow(() -> new RuntimeException("Client non trouvé avec le code: " + codeClient));
            clientRepository.delete(client);
        } catch (Exception e) {
            log.error("Erreur lors de la suppression du client {}", codeClient, e);
            throw new RuntimeException("Erreur lors de la suppression du client: " + e.getMessage());
        }
    }

    private ClientResponse convertToDTO(Client client) {
        ClientResponse dto = new ClientResponse();
        dto.setCodeClient(client.getCodeClient());
        dto.setNomClient(client.getNomClient());
        dto.setEmail(client.getEmail());
        dto.setDateNaissance(client.getDateNaissance());
        dto.setTelephone(client.getTelephone());
        dto.setAdresse(client.getAdresse());
        dto.setVille(client.getVille());
        dto.setPays(client.getPays());

        if (client.getComptes() != null) {
            List<CompteResponse> compteResponses = client.getComptes().stream()
                    .map(this::convertCompteToDTO)
                    .collect(Collectors.toList());
            dto.setComptes(compteResponses);
        }

        return dto;
    }

    private CompteResponse convertCompteToDTO(Compte compte) {
        CompteResponse dto = new CompteResponse();
        dto.setCodeCompte(compte.getCodeCompte());
        dto.setSolde(compte.getSolde());
        dto.setDateCreation(compte.getDateCreation());
        return dto;
    }
}