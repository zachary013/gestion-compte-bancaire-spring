package org.lsi.metier;

import java.util.List;

import org.lsi.dto.ClientRequest;
import org.lsi.dto.ClientResponse;
import org.lsi.dto.CompteResponse;
import org.lsi.entities.Client;
import org.lsi.entities.Compte;

public interface ClientMetier {
    public ClientResponse saveClient(ClientRequest clientRequest);
    public List<ClientResponse> listClient();
    public ClientResponse getClient(Long codeClient);
    public List<CompteResponse> getComptesClient(Long codeClient);
    public ClientResponse updateClient(Long codeClient, ClientRequest clientRequest);
    public void deleteClient(Long codeClient);
}
