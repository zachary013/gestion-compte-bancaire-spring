package org.lsi.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codeClient;

    @NotNull(message = "Le nom du client est obligatoire.")
    @Size(min = 2, max = 50, message = "Le nom du client doit être entre 2 et 50 caractères.")
    private String nomClient;
    private String email;
    private Date dateNaissance;
    private String telephone;
    private String adresse;
    private String ville;
    private String pays;

    @JsonManagedReference(value = "client-compte")
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Compte> comptes;

    // Constructor
    public Client() {}

    public Client(String nomClient) {
        this.nomClient = nomClient;
    }
}