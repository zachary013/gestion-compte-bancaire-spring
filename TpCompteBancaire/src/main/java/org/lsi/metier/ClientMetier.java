package org.lsi.metier;

import java.util.List;
import org.lsi.entities.Client;
import org.lsi.entities.Compte;

public interface ClientMetier {
    public Client saveClient(Client c);
    public List<Client> listClient();
    public List<Compte> getComptesClient(Long codeClient);
    public Client consulterClient(Long codeClient);
    public Client updateClient(Long codeClient, Client updatedClient);
    public void deleteClient(Long codeClient);
}
