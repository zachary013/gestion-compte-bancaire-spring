package org.lsi.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class ClientResponse {
    private Long codeClient;
    private String nomClient;
    private String email;
    private Date dateNaissance;
    private String telephone;
    private String adresse;
    private String ville;
    private String pays;
    private List<CompteResponse> comptes;
}
