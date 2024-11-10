package org.lsi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Date;

@Data
public class ClientRequest {
    @NotNull(message = "Le nom du client est obligatoire.")
    @Size(min = 2, max = 50, message = "Le nom du client doit être entre 2 et 50 caractères.")
    private String nomClient;

    private String email;
    private Date dateNaissance;
    private String telephone;
    private String adresse;
    private String ville;
    private String pays;
}
